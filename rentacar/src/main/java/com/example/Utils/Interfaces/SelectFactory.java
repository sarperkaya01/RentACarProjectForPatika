package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.example.Utils.Global;
import com.example.Utils.Action.MenuAction;

public interface SelectFactory<T, D, S> extends DynamicController {

    S getSelectService();

    default List<String> getAdditionalSelectableFields() {
        return new ArrayList<>();
    }

    // @SuppressWarnings("rawtypes")
    default void runSelectMenu(Class<T> entityType) {
        // Selector fonksiyonu artık genel bir Object döndürecek (List veya Optional)
        Map<String, Function<Object, Object>> fieldSelectors = generateFieldSelectors(entityType);
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

            if (choice == menuActions.size() + 1)
                break;

            if (choice > 0 && choice <= menuActions.size()) {
                menuActions.get(choice - 1).getAction().run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private List<MenuAction> generateSelectActions(Class<T> entityType,
            Map<String, Function<Object, Object>> fieldSelectors) {
        List<MenuAction> actions = new ArrayList<>();
        for (Field field : DynamicController.getAllFields(entityType)) {
            String fieldName = field.getName();
            if (fieldSelectors.containsKey(fieldName)) {
                String displayName = DynamicController.formatFieldName(fieldName);
                // createSelectAction artık yeni selector tipiyle çalışacak
                Runnable action = createSelectAction(field, fieldSelectors.get(fieldName));
                actions.add(new MenuAction(displayName, action));
            }
        }
        return actions;
    }

    private Runnable createSelectAction(Field field, Function<Object, Object> selector) {
        return () -> {
            try {
                if (field.getType().isEnum()) {
                    System.out.println(
                            "Please select a value for " + DynamicController.formatFieldName(field.getName()) + ":");
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

                Object result = selector.apply(convertedValue);

                if (result instanceof List) {
                    List<?> results = (List<?>) result;
                    if (results.isEmpty()) {
                        System.out.println("No records found matching your criteria.");
                    } else {
                        results.forEach(System.out::println);
                    }
                } else if (result instanceof Optional) {
                    Optional<?> optionalResult = (Optional<?>) result;
                    optionalResult.ifPresentOrElse(
                            System.out::println, // Optional doluysa içindekini yazdır
                            () -> System.out.println("No record found matching your criteria.") // Optional boşsa bu
                                                                                                // mesajı yazdır
                    );
                } else {
                    System.out.println("Unsupported result type from service method.");
                }

            } catch (Exception e) {
                System.out.println("Error during search: " + e.getMessage());
            }
        };
    }

    // @SuppressWarnings("unchecked")
    default Map<String, Function<Object, Object>> generateFieldSelectors(Class<T> entityType) {
        S service = getSelectService();
        Map<String, Function<Object, Object>> selectors = new HashMap<>();
        Map<String, Class<?>> allFieldTypes = new HashMap<>();
        DynamicController.getAllFields(entityType)
                .forEach(field -> allFieldTypes.put(field.getName(), field.getType()));

        for (String fieldName : allFieldTypes.keySet()) {
            Class<?> fieldType = allFieldTypes.get(fieldName);
            if (fieldType == null)
                continue;

            String capitalizedField = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            String entityName = entityType.getSimpleName();

            // İSİMLENDİRME KURALI 1: Tekil sonuç (...AsInfoDto)
            String infoMethodName = "get" + entityName + "sBy" + capitalizedField + "AsInfoDto";
            // İSİMLENDİRME KURALI 2: Liste sonuç (...AsListDto)
            String listMethodName = "get" + entityName + "sBy" + capitalizedField + "AsListDto";

            try {
                // Önce InfoDto döndüren metodu ara
                Method selectMethod = service.getClass().getMethod(infoMethodName, fieldType);
                Function<Object, Object> selector = (value) -> {
                    try {
                        return selectMethod.invoke(service, value);
                    } catch (Exception e) {
                        throw new RuntimeException("Optional-Error executing select method: " + infoMethodName, e);
                    }
                };
                selectors.put(fieldName, selector);

            } catch (NoSuchMethodException e) {
                // InfoDto metodu bulunamadıysa, ListDto döndüren metodu ara
                try {
                    Method selectMethod = service.getClass().getMethod(listMethodName, fieldType);
                    Function<Object, Object> selector = (value) -> {
                        try {
                            return selectMethod.invoke(service, value);
                        } catch (Exception ex) {
                            throw new RuntimeException("List-Error executing select method: " + listMethodName, ex);
                        }
                    };
                    selectors.put(fieldName, selector);
                } catch (NoSuchMethodException ex) {
                    // İki metot da bulunamadıysa bu alan aranabilir değildir, sessizce geç.
                }
            }
        }
        return selectors;
    }

}
