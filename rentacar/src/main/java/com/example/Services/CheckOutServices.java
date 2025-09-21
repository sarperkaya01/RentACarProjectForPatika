package com.example.Services;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.DAO.CheckOutDao;
import com.example.DAO.RentalDao;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Entities.Renting.Checkout;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Enums.CheckOutStatus;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;

// Spring'in kendi @Transactional'ını kullanmak bu tür karmaşık işlemlerde en güvenlisidir.
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckOutServices {

}