package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.function.BiConsumer;

import com.example.Entities.DbModels.Vehicles.VehiclePricing;
import com.example.Utils.Global;

import com.example.Utils.Action.MenuAction;

public interface UpdateFactory<T, S> extends DynamicController {

    S getUpdateService();

    /**
     * Bu metot, implemente eden sınıfın, Entity'nin doğrudan kendi alanı olmayan
     * ama menüde gösterilmesini istediği "ilişkili" veya "sanal" alan adlarını
     * bir liste olarak sunmasını sağlar. Varsayılan olarak boş liste döner.
     * 
     * @return Menüye dahil edilecek ek alan adlarının bir listesi.
     */
    default List<String> getAdditionalUpdatableFields() { // <-- İSİM DÜZELTİLDİ
        return new ArrayList<>();
    }

    /**
     * Otomatik güncelleme menüsünü başlatan ana metottur.
     * Bu metot, görev haritasını ve menü aksiyonlarını oluşturup menü döngüsünü
     * çalıştırır.
     * 
     * @param entityId   Güncellenecek olan nesnenin veritabanı ID'si.
     * @param entityType Güncellenecek olan nesnenin Class tipi (örn:
     *                   Automobile.class).
     */
    default void runUpdateMenu(Integer entityId, Class<T> entityType) {
        if (entityId == null) {
            System.out.println("Cannot start update menu without a valid ID.");
            return;
        }

        Map<String, BiConsumer<Integer, Object>> fieldUpdaters = generateFieldUpdaters(entityType);
        List<MenuAction> menuActions = generateUpdateActions(entityId, entityType, fieldUpdaters);

        while (true) {
            System.out.println("\n--- Update " + entityType.getSimpleName() + " Menu ---");
            for (int i = 0; i < menuActions.size(); i++) {
                System.out.println((i + 1) + ". " + menuActions.get(i).getDisplayName());
            }
            System.out.println((menuActions.size() + 1) + ". Back");

            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(Global.scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == menuActions.size() + 1)
                break;

            if (choice > 0 && choice <= menuActions.size()) {
                menuActions.get(choice - 1).getAction().run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Oluşturulan görev haritasını kullanarak, menüde gösterilecek her bir
     * satır için (örn: "Update Plate") bir 'MenuAction' nesnesi üretir.
     * Bu metot, 'DynamicActionFactory' sınıfının görevini üstlenmiştir.
     */
    private List<MenuAction> generateUpdateActions(Integer entityId, Class<T> entityType,
            Map<String, BiConsumer<Integer, Object>> fieldUpdaters) {
        List<MenuAction> actions = new ArrayList<>();

        // --- Referans için tüm alanların bir haritasını oluşturalım ---
        Map<String, Field> allFieldsMap = new HashMap<>();
        // Önce ana entity'nin alanları...
        for (Field field : DynamicController.getAllFields(entityType)) {
            allFieldsMap.put(field.getName(), field);
        }

        for (Field field : DynamicController.getAllFields(VehiclePricing.class)) {
            allFieldsMap.putIfAbsent(field.getName(), field);
        }

        // --- Menüyü oluşturalım ---
        // 1. Önce Entity'nin kendi alanlarını ekle
        for (Field field : DynamicController.getAllFields(entityType)) {
            String fieldName = field.getName();
            if (fieldUpdaters.containsKey(fieldName)) {
                String displayName = "Update " + DynamicController.formatFieldName(fieldName);
                Runnable action = createUpdateAction(field, entityId, fieldUpdaters.get(fieldName));
                actions.add(new MenuAction(displayName, action));
            }
        }

        // 2. Şimdi de "ek" alanları ekle
        for (String additionalFieldName : getAdditionalUpdatableFields()) {
            if (fieldUpdaters.containsKey(additionalFieldName)) {
                Field field = allFieldsMap.get(additionalFieldName);
                if (field != null) {
                    String displayName = "Update " + DynamicController.formatFieldName(additionalFieldName);
                    Runnable action = createUpdateAction(field, entityId, fieldUpdaters.get(additionalFieldName));
                    actions.add(new MenuAction(displayName, action));
                }
            }
        }

        return actions;
    }

    /**
     * Menüdeki tek bir seçenek ("Update Plate" gibi) seçildiğinde çalışacak olan
     * 'Runnable' komutunu oluşturur. Bu komut, kullanıcıdan veri alır,
     * veriyi doğru tipe çevirir ve ilgili servis metodunu çağırır.
     */
    private Runnable createUpdateAction(Field field, Integer entityId, BiConsumer<Integer, Object> updater) {
        return () -> {
            try {
                // Alanın tipi Enum mu diye kontrol et
                if (field.getType().isEnum()) {
                    System.out.println("Please select a new value for "
                            + DynamicController.formatFieldName(field.getName()) + ":");
                    // Enum'un tüm olası değerlerini al ve listele
                    Object[] enumConstants = field.getType().getEnumConstants();
                    for (int i = 0; i < enumConstants.length; i++) {
                        System.out.println((i + 1) + ". " + enumConstants[i].toString());
                    }
                    System.out.print("Enter your choice (number): ");
                } else {
                    System.out
                            .print("Enter new value for " + DynamicController.formatFieldName(field.getName()) + ": ");
                }

                String input = Global.scanner.nextLine();
                // Artık akıllı olan convertInput metodunu çağır
                Object convertedValue = DynamicController.convertInput(input, field.getType());
                updater.accept(entityId, convertedValue);
                System.out.println(DynamicController.formatFieldName(field.getName()) + " updated successfully.");
            } catch (Exception e) {
                System.out.println("Error updating field: " + e.getMessage());
            }
        };
    }

    /**
     * Yansıma (reflection) kullanarak, Entity'nin alan adları ile Servis'in metot
     * adları arasında
     * bir "görev haritası" oluşturur. Örneğin, 'plate' alanını 'updatePlate'
     * metoduna bağlar.
     * 
     * @param entityType Taranacak olan Entity'nin Class'ı.
     * @return Alan adları ve onları güncelleyecek fonksiyonları içeren bir Map.
     */
    default Map<String, BiConsumer<Integer, Object>> generateFieldUpdaters(Class<T> entityType) {
        S service = getUpdateService();
        Map<String, BiConsumer<Integer, Object>> updaters = new HashMap<>();

        // --- Referans için tüm olası alanların tiplerini bir haritada toplayalım ---
        Map<String, Class<?>> allFieldTypes = new HashMap<>();
        for (Field field : DynamicController.getAllFields(entityType)) {
            allFieldTypes.put(field.getName(), field.getType());
        }
        for (Field field : DynamicController.getAllFields(VehiclePricing.class)) {
            allFieldTypes.putIfAbsent(field.getName(), field.getType());
        }

        // --- Şimdi tüm taranması gereken alan adlarını tek bir listede birleştirelim
        // ---
        List<String> allFieldNamesToScan = new ArrayList<>();
        // Önce entity'nin kendi alan adları
        for (Field field : DynamicController.getAllFields(entityType)) {
            allFieldNamesToScan.add(field.getName());
        }
        // Sonra da "ek" alan adları
        allFieldNamesToScan.addAll(getAdditionalUpdatableFields());

        // --- Şimdi bu birleşik liste üzerinde dönelim ve metotları arayalım ---
        for (String fieldName : allFieldNamesToScan) {
            Class<?> fieldType = allFieldTypes.get(fieldName);
            if (fieldType == null)
                continue; // Tipi bilinmeyen alanı atla

            try {
                String methodName = "update" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Method updateMethod = service.getClass().getMethod(methodName, Integer.class, fieldType);

                BiConsumer<Integer, Object> updater = (id, value) -> {
                    try {
                        updateMethod.invoke(service, id, value);
                    } catch (Exception e) {
                        throw new RuntimeException("Error executing update method: " + methodName, e);
                    }
                };
                updaters.put(fieldName, updater);
            } catch (NoSuchMethodException e) {
                // Metot bulunamadıysa sessizce geç
            }
        }

        return updaters;
    }

}
