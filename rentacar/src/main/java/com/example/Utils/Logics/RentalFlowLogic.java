package com.example.Utils.Logics;

import java.util.Map;
import java.util.Set;

import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalStatus;

public class RentalFlowLogic {
    private RentalFlowLogic() {
    }

    private static final Map<RentalStatus, Set<RentalStatus>> VALID_RENTAL_TRANSITIONS;
    private static final Map<CheckoutStatus, Set<CheckoutStatus>> VALID_CHECKOUT_TRANSITIONS;

    static {
        VALID_RENTAL_TRANSITIONS = Map.of(
            // Bir kiralama RENTED durumundayken sadece COMPLETED durumuna geçebilir.
            RentalStatus.RENTED, Set.of(RentalStatus.COMPLETED)
            // COMPLETED durumundaki bir kiralama başka hiçbir duruma geçemez.
            // Bu yüzden map'te anahtar olarak bile yer almasına gerek yok.
        );

        VALID_CHECKOUT_TRANSITIONS = Map.of(
            // Bir ödeme IN_PROGRESS durumundayken sadece PAID durumuna geçebilir.
             CheckoutStatus.IN_PROGRESS, Set.of(CheckoutStatus.PAID, CheckoutStatus.LATE),
            // LATE durumundayken de PAID durumuna geçebilir.
            CheckoutStatus.LATE, Set.of(CheckoutStatus.PAID)
        );
       
    }

    public static boolean canTransition(RentalStatus from, RentalStatus to) {
        Set<RentalStatus> validNextStates = VALID_RENTAL_TRANSITIONS.get(from);
        return validNextStates != null && validNextStates.contains(to);
    }

    public static boolean canTransition(CheckoutStatus from, CheckoutStatus to) {
        Set<CheckoutStatus> validNextStates = VALID_CHECKOUT_TRANSITIONS.get(from);
        return validNextStates != null && validNextStates.contains(to);
    }

    public static boolean isFinalState(RentalStatus status) {
        // Geçiş haritasında "from" (anahtar) olarak bulunmayan durumlar, nihai
        // durumlardır.
        return !VALID_RENTAL_TRANSITIONS.containsKey(status);
    }
     public static boolean areStatesConsistent(RentalStatus rentalStatus, CheckoutStatus checkoutStatus) {
        if (rentalStatus == null || checkoutStatus == null) {
            return false; // Durumlar boş olamaz.
        }

        switch (rentalStatus) {
            case RENTED:
                // Kural: Kiralama RENTED durumundaysa, ödeme sadece IN_PROGRESS veya LATE olabilir.
                return checkoutStatus == CheckoutStatus.IN_PROGRESS || checkoutStatus == CheckoutStatus.LATE;
            
            case COMPLETED:
                // Kural: Kiralama COMPLETED ise, ödeme sadece PAID olabilir.
                return checkoutStatus == CheckoutStatus.PAID;
            
            default:
                // Bilinmeyen bir kiralama durumu için her zaman false dönmek en güvenlisidir.
                return false;
        }
    }
}
