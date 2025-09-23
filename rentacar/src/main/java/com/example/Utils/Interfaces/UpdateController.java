package com.example.Utils.Interfaces;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.function.BiConsumer;

import com.example.Utils.Global;
import com.example.Utils.Action.DynamicActionFactory;
import com.example.Utils.Action.MenuAction;
public interface UpdateController<T,S> {

    void start();
    S getUpdateService();
     default Map<String, BiConsumer<Integer, Object>> generateFieldUpdaters(Class<T> entityType) {
        S service = getUpdateService();
        Map<String, BiConsumer<Integer, Object>> updaters = new HashMap<>();
        List<Field> fields = getAllFields(entityType);

        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                // Kural: Metot adı "update" + "AlanAdı" (ilk harfi büyük) olmalı.
                // Örn: 'plate' alanı için 'updatePlate' metodu aranır.
                String methodName = "update" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

                // Servis üzerinde bu isimde ve doğru parametrelerde bir metot bul.
                // Metot imzası: updateFieldName(Integer id, FieldType type)
                Method updateMethod = service.getClass().getMethod(methodName, Integer.class, field.getType());

                // Metot bulunduysa, onu çağıran bir BiConsumer oluştur ve haritaya ekle.
                BiConsumer<Integer, Object> updater = (id, value) -> {
                    try {
                        updateMethod.invoke(service, id, value);
                    } catch (Exception e) {
                        throw new RuntimeException("Error executing update method: " + methodName, e);
                    }
                };
                updaters.put(fieldName, updater);

            } catch (NoSuchMethodException e) {
                // Servis sınıfında bu alan için bir update metodu bulunamadı.
                // Bu beklenen bir durum olabilir (örn: 'id' alanı için). O yüzden sessizce geç.
            }
        }
        return updaters;
    }
    
    

    
    default void runUpdateMenu(Integer entityId, Class<T> entityType, String menuTitle) {
        // 1. Güncelleme haritasını OTOMATİK olarak oluştur.
        Map<String, BiConsumer<Integer, Object>> fieldUpdaters = generateFieldUpdaters(entityType);
        
        // --- Geri kalan her şey aynı ---
        DynamicActionFactory<T> factory = new DynamicActionFactory<>(entityId, entityType,  fieldUpdaters);
        List<MenuAction> menuActions = factory.generateActions();
        
        while (true) {
            System.out.println("\n--- " + menuTitle + " ---");
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

            if (choice == menuActions.size() + 1) {
                break;
            }

            if (choice > 0 && choice <= menuActions.size()) {
                menuActions.get(choice - 1).getAction().run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Bir sınıfın ve tüm üst sınıflarının alanlarını getiren yardımcı metot.
    private List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

}
