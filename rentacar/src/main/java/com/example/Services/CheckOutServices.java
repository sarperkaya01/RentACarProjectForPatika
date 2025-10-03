package com.example.Services;

import com.example.DAO.CheckoutDao;
import com.example.DAO.RentalDao;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.DbModels.Vehicles.VehiclePricing;
import com.example.Entities.Renting.Checkout;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Global;
import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalPeriod;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Interfaces.AirVehicle;
import com.example.Utils.Interfaces.LandVehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class CheckoutServices {

    private final CheckoutDao checkoutDao;
    private final RentalDao rentalDao;
    private final CheckoutCalculatorService checkoutCalculatorService;

    @Autowired
    public CheckoutServices(CheckoutDao checkoutDao, RentalDao rentalDao,
            CheckoutCalculatorService checkoutCalculatorService) {
        this.checkoutDao = checkoutDao;
        this.rentalDao = rentalDao;
        this.checkoutCalculatorService = checkoutCalculatorService;
    }

    // --- CRUD: READ ---

    @Transactional(readOnly = true)
    public List<Checkout> getAllCheckouts() {
        return checkoutDao.findAll();
    }

    public Checkout getCheckoutById(Integer checkoutId) {
        return checkoutDao.findById(checkoutId)
                .orElseThrow(() -> new IllegalStateException("Checkout not found with id: " + checkoutId));
    }

    // --- CRUD: UPDATE ---

    @Transactional
    public Checkout finalizeRental(Integer rentalId, BigDecimal distanceTraveled, BigDecimal repairFee) {
        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> new IllegalStateException("Rental not found with ID: " + rentalId));

        if (rental.getRentalStatus() == RentalStatus.COMPLETED) {
            throw new IllegalStateException("This rental has already been completed.");
        }

        BigDecimal finalRepairFee = Objects.requireNonNullElse(repairFee, BigDecimal.ZERO);
        if (finalRepairFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Repair fee cannot be negative.");
        }

        Checkout checkout = rental.getCheckout();
        Vehicle vehicle = rental.getVehicle();
        Customer customer = rental.getCustomer();

        updateVehicleDistance(vehicle, distanceTraveled);

        LocalDateTime now = LocalDateTime.now();
        BigDecimal lateFee = calculateLateFee(checkout.getPlannedDropoffDate(), now, vehicle.getPricing());

        BigDecimal subTotal = checkout.getPlannedPrice()
                .add(lateFee)
                .add(finalRepairFee);

        BigDecimal deposit = checkout.getDeposit();
        BigDecimal finalPaymentDue = subTotal.subtract(deposit);

        if (customer.getBudget().compareTo(finalPaymentDue) < 0) {
            throw new IllegalStateException("Insufficient budget. Customer needs to pay " + finalPaymentDue
                    + " but only has " + customer.getBudget());
        }

        customer.setBudget(customer.getBudget().subtract(finalPaymentDue));

        checkout.setActualDropoffDate(now);
        checkout.setRepairFee(finalRepairFee);
        checkout.setLateFee(lateFee);
        checkout.setCheckoutAmount(subTotal);
        checkout.setCheckoutStatus(CheckoutStatus.PAID);

        rental.setRentalStatus(RentalStatus.COMPLETED);
        vehicle.setVehicleStatus(VehicleStatus.AVAILABLE);

        return checkout;
    }

    /**
     * Planlanan ve gerçek teslim tarihleri arasındaki farka göre gecikme ücretini
     * hesaplar.
     *
     * @param plannedDate Planlanan teslim tarihi.
     * @param actualDate  Gerçek teslim tarihi.
     * @param pricing     Gecikme ücretini hesaplamak için aracın fiyatlandırma
     *                    profili.
     * @return Hesaplanan gecikme ücreti. Araç geç değilse 0 döner.
     */
    private BigDecimal calculateLateFee(LocalDateTime plannedDate, LocalDateTime actualDate, VehiclePricing pricing) {
        if (actualDate.isBefore(plannedDate) || actualDate.isEqual(plannedDate)) {
            return BigDecimal.ZERO;
        }

        long daysLate = ChronoUnit.DAYS.between(plannedDate, actualDate);

        if (daysLate == 0) {

            return pricing.getDailyPricing().divide(new BigDecimal("2"));
        }

        return pricing.getDailyPricing().multiply(new BigDecimal(daysLate));
    }

    public Checkout saveNewCheckout(Vehicle vehicle) {
        System.out.println("--- New Rental Process ---");

        try {
            RentalPeriod selectedPeriod;

            while (true) {
                System.out.println("\nSelect rental period:");
                System.out.println("1. Hourly");
                System.out.println("2. Daily");
                System.out.println("3. Weekly");
                System.out.println("4. Monthly");
                System.out.print("Your choice: ");

                int periodChoice = Integer.parseInt(Global.scanner.nextLine());

                switch (periodChoice) {
                    case 1:
                        selectedPeriod = RentalPeriod.HOURLY;
                        break;
                    case 2:
                        selectedPeriod = RentalPeriod.DAILY;
                        break;
                    case 3:
                        selectedPeriod = RentalPeriod.WEEKLY;
                        break;
                    case 4:
                        selectedPeriod = RentalPeriod.MONTHLY;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        continue;
                }
                break;
            }

            System.out.print("Enter duration (e.g., for 5 days enter 5): ");
            int duration = Integer.parseInt(Global.scanner.nextLine());

            Checkout preparedCheckout = checkoutCalculatorService.prepareCheckout(vehicle, selectedPeriod,
                    duration);

            System.out.println("\n--- RENTAL SUMMARY ---");
            System.out.printf("Planned Price: %.2f\n", preparedCheckout.getPlannedPrice());
            System.out.printf("Deposit      : %.2f\n", preparedCheckout.getDeposit());
            System.out.println("Return Date  : "
                    + preparedCheckout.getPlannedDropoffDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            System.out.print("\nDo you confirm this rental? (Y/N): ");

            String confirmation = Global.scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {

                return checkoutDao.save(preparedCheckout);

            } else {
                System.out.println("Rental canceled.");
                return null;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private void updateVehicleDistance(Vehicle vehicle, BigDecimal distance) {

        if (distance == null || distance.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        if (vehicle instanceof LandVehicle) {

            ((LandVehicle) vehicle).addKm(distance);

        } else if (vehicle instanceof AirVehicle) {

            ((AirVehicle) vehicle).addFlightHours(distance);
        }

    }
}