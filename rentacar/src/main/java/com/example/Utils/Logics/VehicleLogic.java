package com.example.Utils.Logics;

import java.util.Map;
import java.util.Set;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

public final class VehicleLogic {

     private VehicleLogic() {} 

    
    private static final Map<VehicleTypes, Set<VehicleStatus>> VALID_STATUSES_FOR_TYPE;

    
    static {
        VALID_STATUSES_FOR_TYPE = Map.of(
            VehicleTypes.AUTOMOBILE, 
            Set.of(VehicleStatus.AVAILABLE, VehicleStatus.RENTED, VehicleStatus.DAMAGED, VehicleStatus.UNDER_MAINTENANCE),

            VehicleTypes.MOTORCYCLE,
            Set.of(VehicleStatus.AVAILABLE, VehicleStatus.RENTED, VehicleStatus.DAMAGED, VehicleStatus.UNDER_MAINTENANCE),

            VehicleTypes.HELICOPTER,
            Set.of(VehicleStatus.AVAILABLE, VehicleStatus.RENTED, VehicleStatus.UNDER_MAINTENANCE) // Mesela helikopter 'DAMAGED' olamıyor.
        );
        //helicopteri sadece sirket sahipleri kullanabilir
        //degeri 2m den buyuk araclardan %10 depozito alinir-ve iade edilir...
    }

    // Bu ilişkiyi kontrol eden metot.
    public static boolean isValidStatusForType(VehicleTypes type, VehicleStatus status) {
        Set<VehicleStatus> validStatuses = VALID_STATUSES_FOR_TYPE.get(type);
        return validStatuses != null && validStatuses.contains(status);
    }

}
