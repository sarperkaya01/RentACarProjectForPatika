package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.example.Utils.Global;

import com.example.Utils.Action.MenuAction;

public interface UpdateController<T, S> {

    S getUpdateService();

    default void runUpdateMenu(Integer entityId, Class<T> entityType) {
        if (entityId == null) {
            System.out.println("Cannot start update menu without a valid ID.");
            return;
        }

        // 1. Görev haritasını otomatik oluştur.
        Map<String, BiConsumer<Integer, Object>> fieldUpdaters = generateFieldUpdaters(entityType);

        // 2. Bu haritayı kullanarak menü aksiyonlarını doğrudan burada oluştur.
        List<MenuAction> menuActions = generateUpdateActions(entityId, entityType, fieldUpdaters);

        // 3. Menü döngüsünü başlat.
        while (true) {
            System.out.println("\n--- Update " + entityType.getName() + " Menu ---");
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

    /**
     * Verilen görev haritasını kullanarak menü aksiyonlarını üretir.
     * Bu metot artık DynamicActionFactory'nin işini yapar.
     */
    private List<MenuAction> generateUpdateActions(Integer entityId, Class<T> entityType,
            Map<String, BiConsumer<Integer, Object>> fieldUpdaters) {
        List<MenuAction> actions = new ArrayList<>();
        List<Field> fields = getAllFields(entityType)
                .stream()
                .filter(field -> fieldUpdaters.containsKey(field.getName()))
                .collect(Collectors.toList());

        for (Field field : fields) {
            String fieldName = field.getName();
            String displayName = "Update " + formatFieldName(fieldName);
            Runnable action = createUpdateAction(field, entityId, fieldUpdaters.get(fieldName));
            actions.add(new MenuAction(displayName, action));
        }
        return actions;
    }

    /**
     * Tek bir güncelleme aksiyonu (Runnable) oluşturur.
     */
    private Runnable createUpdateAction(Field field, Integer entityId, BiConsumer<Integer, Object> updater) {
        return () -> {
            try {
                System.out.print("Enter new value for " + formatFieldName(field.getName()) + ": ");
                String input = Global.scanner.nextLine();
                Object convertedValue = convertInput(input, field.getType());
                updater.accept(entityId, convertedValue);
                System.out.println(formatFieldName(field.getName()) + " updated successfully.");
            } catch (Exception e) {
                System.out.println("Error updating field: " + e.getMessage());
            }
        };
    }

    /**
     * Yansıma (reflection) kullanarak Entity ve Servis sınıfları arasında
     * otomatik olarak bir "görev haritası" oluşturur.
     */
    default Map<String, BiConsumer<Integer, Object>> generateFieldUpdaters(Class<T> entityType) {
        S service = getUpdateService();
        Map<String, BiConsumer<Integer, Object>> updaters = new HashMap<>();
        List<Field> fields = getAllFields(entityType);

        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                String methodName = "update" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Method updateMethod = service.getClass().getMethod(methodName, Integer.class, field.getType());

                BiConsumer<Integer, Object> updater = (id, value) -> {
                    try {
                        updateMethod.invoke(service, id, value);
                    } catch (Exception e) {
                        throw new RuntimeException("Error executing update method: " + methodName, e);
                    }
                };
                updaters.put(fieldName, updater);

            } catch (NoSuchMethodException e) {
                // Bu alan için bir update metodu yoksa sessizce geç.
            }
        }
        return updaters;
    }

    // --- ÖZEL YARDIMCI (PRIVATE) METOTLAR ---
    // Bu metotlar sadece bu arayüz içinde kullanılır ve dışarıdan erişilemez.

    private List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    private String formatFieldName(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return "";
        }
        StringBuilder formattedName = new StringBuilder();
        formattedName.append(Character.toUpperCase(fieldName.charAt(0)));
        for (int i = 1; i < fieldName.length(); i++) {
            char currentChar = fieldName.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                formattedName.append(' ');
            }
            formattedName.append(currentChar);
        }
        return formattedName.toString();
    }

    private Object convertInput(String input, Class<?> targetType) {
        if (targetType.equals(String.class))
            return input;
        if (targetType.equals(Integer.class))
            return Integer.parseInt(input);
        if (targetType.equals(BigDecimal.class))
            return new BigDecimal(input);
        if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, input.toUpperCase());
        }
        throw new IllegalArgumentException("Unsupported field type for conversion: " + targetType.getSimpleName());
    }

}
