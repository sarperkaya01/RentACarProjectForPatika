package com.example.Utils.Logics;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Enums.VehicleTypes;

public class PermissionLogic {

      private PermissionLogic() {}

    private static final Map<UserRoles, Set<VehicleTypes>> RENTABLE_VEHICLES_BY_ROLE;

    private static final Map<UserRoles, Map<VehicleTypes, BigDecimal>> DISCOUNT_RATES_BY_ROLE;

    static {
        RENTABLE_VEHICLES_BY_ROLE = Map.of(
            // BİREYSEL kullanıcılar sadece otomobil ve motosiklet kiralayabilir.
            UserRoles.INDIVIDUAL,
            Set.of(VehicleTypes.AUTOMOBILE, VehicleTypes.MOTORCYCLE),
            
            //PERSONEL INDIRIMI EKLENECEK
            UserRoles.EMPLOYEE,
            Set.of(VehicleTypes.AUTOMOBILE, VehicleTypes.MOTORCYCLE),

            // KURUMSAL kullanıcılar her şeyi kiralayabilir.
            UserRoles.CORPORATE,
            Set.of(VehicleTypes.AUTOMOBILE, VehicleTypes.MOTORCYCLE, VehicleTypes.HELICOPTER),

            // ADMIN kullanıcılar (genellikle test veya yönetim için) her şeyi kiralayabilir.
            UserRoles.ADMIN,
            Set.of(VehicleTypes.AUTOMOBILE, VehicleTypes.MOTORCYCLE, VehicleTypes.HELICOPTER));

            DISCOUNT_RATES_BY_ROLE = Map.of(
            // Sadece EMPLOYEE rolü için bir indirim haritası tanımlıyoruz.
            UserRoles.EMPLOYEE, Map.of(
                VehicleTypes.AUTOMOBILE, new BigDecimal("0.10"), // Otomobilde %10 indirim
                VehicleTypes.MOTORCYCLE, new BigDecimal("0.15")  // Motosiklette %15 indirim
            )
            // Diğer rollerin indirimi olmadığı için buraya eklemeye gerek yok.
        );
        
    }
    

    public static boolean canUserRentVehicleType(UserRoles userRole, VehicleTypes vehicleType) {
        Set<VehicleTypes> allowedVehicles = RENTABLE_VEHICLES_BY_ROLE.get(userRole);
        return allowedVehicles != null && allowedVehicles.contains(vehicleType);
    }

    public static BigDecimal getDiscountRate(UserRoles userRole, VehicleTypes vehicleType) {
        // Önce role ait bir indirim haritası var mı diye kontrol et.
        Map<VehicleTypes, BigDecimal> vehicleDiscounts = DISCOUNT_RATES_BY_ROLE.get(userRole);
        if (vehicleDiscounts == null) {
            return BigDecimal.ZERO; // Bu rol için hiç indirim tanımlanmamış.
        }
        // Role ait harita varsa, o haritadan araç tipine özel indirimi al.
        // Eğer o araç tipi için özel indirim yoksa, varsayılan olarak 0 dön.
        return vehicleDiscounts.getOrDefault(vehicleType, BigDecimal.ZERO);
    }

}
