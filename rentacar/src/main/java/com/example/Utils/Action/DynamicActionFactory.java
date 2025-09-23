package com.example.Utils.Action;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.example.Utils.Global;

public class DynamicActionFactory<T> {

    private final Integer entityId;
    private final Class<T> entityType;

    private final Map<String, BiConsumer<Integer, Object>> fieldUpdaters;

    public DynamicActionFactory(Integer entityId, Class<T> entityType,
            Map<String, BiConsumer<Integer, Object>> fieldUpdaters) {
        this.entityId = entityId;
        this.entityType = entityType;

        this.fieldUpdaters = fieldUpdaters;
    }

    public List<MenuAction> generateActions() {
        List<MenuAction> actions = new ArrayList<>();
        List<Field> fields = getAllFields(entityType);

        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldUpdaters.containsKey(fieldName)) {
                String displayName = "Update " + formatFieldName(fieldName);
                Runnable action = createUpdateAction(field);
                actions.add(new MenuAction(displayName, action));
            }
        }
        return actions;
    }

    private Runnable createUpdateAction(Field field) {
        return () -> {
            try {
                System.out.print("Enter new value for " + formatFieldName(field.getName()) + ": ");
                String input = Global.scanner.nextLine();
                Object convertedValue = convertInput(input, field.getType());

                BiConsumer<Integer, Object> updater = fieldUpdaters.get(field.getName());
                updater.accept(entityId, convertedValue);

                System.out.println(formatFieldName(field.getName()) + " updated successfully.");
            } catch (Exception e) {
                System.out.println("Error updating field: " + e.getMessage());
            }
        };
    }

    private Object convertInput(String input, Class<?> targetType) {
        if (targetType.equals(String.class)) {
            return input;
        }
        if (targetType.equals(Integer.class)) {
            return Integer.parseInt(input);
        }
        if (targetType.equals(BigDecimal.class)) {
            return new BigDecimal(input);
        }
        if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, input.toUpperCase());
        }
        throw new IllegalArgumentException("Unsupported field type for conversion: " + targetType.getSimpleName());
    }

    private List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields.stream()
                .filter(field -> fieldUpdaters.containsKey(field.getName()))
                .collect(Collectors.toList());
    }

    private String formatFieldName(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return "";
        }

        StringBuilder formattedName = new StringBuilder();
        // İlk harfi her zaman büyük yap.
        formattedName.append(Character.toUpperCase(fieldName.charAt(0)));

        // Geri kalan karakterler için döngü.
        for (int i = 1; i < fieldName.length(); i++) {
            char currentChar = fieldName.charAt(i);
            // Eğer karakter büyük harf ise, önüne bir boşluk ekle.
            if (Character.isUpperCase(currentChar)) {
                formattedName.append(' ');
            }
            formattedName.append(currentChar);
        }

        return formattedName.toString();
    }

}
