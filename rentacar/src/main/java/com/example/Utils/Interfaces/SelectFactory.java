package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.example.Utils.Global;
import com.example.Utils.Action.MenuAction;

public interface SelectFactory<T, D, S> extends DynamicController {

    S getSelectService();

    default List<String> getAdditionalSelectableFields() {
        return new ArrayList<>();
    }

    //@SuppressWarnings("unchecked")
    default void runSelectMenu(Class<T> entityType) {
        Map<String, Function<Object, List<D>>> fieldSelectors = generateFieldSelectors(entityType);
        List<MenuAction> menuActions = generateSelectActions(entityType, fieldSelectors);

        while (true) {
            System.out.println("\n--- Search " + entityType.getSimpleName() + " Menu ---");
            System.out.println("Please select a field to search by:");
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

            if (choice == menuActions.size() + 1) break;

            if (choice > 0 && choice <= menuActions.size()) {
                menuActions.get(choice - 1).getAction().run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private List<MenuAction> generateSelectActions(Class<T> entityType, Map<String, Function<Object, List<D>>> fieldSelectors) {
        List<MenuAction> actions = new ArrayList<>();
        Map<String, Field> allFieldsMap = new HashMap<>();
        DynamicController.getAllFields(entityType).forEach(field -> allFieldsMap.put(field.getName(), field));

        for (Field field : DynamicController.getAllFields(entityType)) {
            String fieldName = field.getName();
            if (fieldSelectors.containsKey(fieldName)) {
                String displayName = DynamicController.formatFieldName(fieldName);
                Runnable action = createSelectAction(field, fieldSelectors.get(fieldName));
                actions.add(new MenuAction(displayName, action));
            }
        }
        return actions;
    }

    private Runnable createSelectAction(Field field, Function<Object, List<D>> selector) {
        return () -> {
            try {
                if (field.getType().isEnum()) {
                    System.out.println("Please select a value for " + DynamicController.formatFieldName(field.getName()) + ":");
                    Object[] enumConstants = field.getType().getEnumConstants();
                    for (int i = 0; i < enumConstants.length; i++) {
                        System.out.println((i + 1) + ". " + enumConstants[i].toString());
                    }
                    System.out.print("Enter your choice (number): ");
                } else {
                    System.out.print("Enter value for " + DynamicController.formatFieldName(field.getName()) + ": ");
                }

                String input = Global.scanner.nextLine();
                Object convertedValue = DynamicController.convertInput(input, field.getType());
                
                System.out.println("\n--- Search Results ---");
                List<D> results = selector.apply(convertedValue);

                if (results.isEmpty()) {
                    System.out.println("No records found matching your criteria.");
                } else {
                    results.forEach(System.out::println);
                }

            } catch (Exception e) {
                System.out.println("Error during search: " + e.getMessage());
            }
        };
    }

    @SuppressWarnings("unchecked")
    default Map<String, Function<Object, List<D>>> generateFieldSelectors(Class<T> entityType) {
        S service = getSelectService();
        Map<String, Function<Object, List<D>>> selectors = new HashMap<>();
        Map<String, Class<?>> allFieldTypes = new HashMap<>();
        DynamicController.getAllFields(entityType).forEach(field -> allFieldTypes.put(field.getName(), field.getType()));

        for (String fieldName : allFieldTypes.keySet()) {
            Class<?> fieldType = allFieldTypes.get(fieldName);
            if (fieldType == null) continue;

            try {
                // KURAL: Servis metodunun adı `get...By[FieldName]AsDto` formatında olmalı.
                // Örnek: getAutomobilesByBrandNameAsDto
                String capitalizedField = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                String methodName = "get" + entityType.getSimpleName() + "sBy" + capitalizedField + "AsDto";
                
                Method selectMethod = service.getClass().getMethod(methodName, fieldType);

                Function<Object, List<D>> selector = (value) -> {
                    try {
                        return (List<D>) selectMethod.invoke(service, value);
                    } catch (Exception e) {
                        throw new RuntimeException("Error executing select method: " + methodName, e);
                    }
                };
                selectors.put(fieldName, selector);
            } catch (NoSuchMethodException e) {
                // Metot bulunamadıysa sessizce geç. Bu alan arama kriteri değildir.
            }
        }
        return selectors;
    }

}
