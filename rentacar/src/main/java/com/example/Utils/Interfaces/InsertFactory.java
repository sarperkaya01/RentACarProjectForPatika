package com.example.Utils.Interfaces;

import com.example.Utils.Global;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface InsertFactory<T, S> extends DynamicController {

    S getSavingService();

    default String getSaveMethodName(Class<T> entityType) {
        return "saveNew" + entityType.getSimpleName();
    }

    default List<String> getFieldsToSkip() {
        return new ArrayList<>();
    }

    default Map<String, Object> collectCustomFieldData() throws Exception {
        return new HashMap<>();
    }

    default T runDynamicInsertMenu(Class<T> entityType) {
        System.out.println("\n--- Create New " + entityType.getSimpleName() + " ---");
        try {
            // 1. Standart alanlar için verileri topla
            Map<String, Object> fieldValues = collectFieldData(entityType);

            // 2. Özel alanlar için verileri topla ve standart olanlarla birleştir
            fieldValues.putAll(collectCustomFieldData());

            // 3. Nesneyi yarat ve doldur
            T newInstance = createAndPopulateInstance(entityType, fieldValues);

            // 4. Nesneyi kaydet
            String methodName = getSaveMethodName(entityType);
            if (methodName != "-") {
                S service = getSavingService();
                Method saveMethod = service.getClass().getMethod(methodName, entityType);

                // Servis metodunun geri döndürdüğü (ID'si atanmış) nesneyi yakala
                Object savedObject = saveMethod.invoke(service, newInstance);

                System.out.println("\n--- SUCCESS ---");
                System.out.println(entityType.getSimpleName() + " has been successfully created.");

                // Kaydedilmiş ve son haliyle güncel olan nesneyi geri döndür
                return entityType.cast(savedObject);
            }

            return newInstance;

        } catch (Exception e) {
            System.out.println("\n--- ERROR ---");
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            System.out.println("An error occurred during creation: " + cause.getMessage());

            // Hata durumunda null döndür
            return null;
        }
    }

    private Map<String, Object> collectFieldData(Class<T> entityType) throws Exception {
        Map<String, Object> fieldValues = new HashMap<>();
        List<String> fieldsToSkip = getFieldsToSkip();

        for (Field field : DynamicController.getAllFields(entityType)) {

            if (fieldsToSkip.contains(field.getName())) {
                continue;
            }
            fieldValues.put(field.getName(), promptForField(field));
        }
        return fieldValues;
    }

    private T createAndPopulateInstance(Class<T> entityType, Map<String, Object> fieldValues) throws Exception {
        Constructor<T> constructor = entityType.getDeclaredConstructor();
        T instance = constructor.newInstance();

        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            try {
                Field field = findField(entityType, fieldName); // Kalıtımı da arayan yardımcı metot
                String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Method setter = entityType.getMethod(setterName, field.getType());
                setter.invoke(instance, value);
            } catch (NoSuchFieldException e) {
                // Bu alanın entity'de karşılığı olmayabilir (örn: passwordConfirm),

            }
        }
        return instance;
    }

    private Object promptForField(Field field) throws Exception {
        // Alanın tipi Enum mu diye kontrol et
        if (field.getType().isEnum()) {
            System.out.println("Please select a value for " + DynamicController.formatFieldName(field.getName()) + ":");
            // Enum'un tüm olası değerlerini al ve listele
            Object[] enumConstants = field.getType().getEnumConstants();
            for (int i = 0; i < enumConstants.length; i++) {
                System.out.println((i + 1) + ". " + enumConstants[i].toString());
            }
            System.out.print("Enter your choice (number): ");
        } else {
            // Enum değilse, standart istemi göster
            System.out.print("Enter " + DynamicController.formatFieldName(field.getName()) + ": ");
        }

        String input = Global.scanner.nextLine();
        // convertInput metodu hem "Admin" gibi metinleri hem de "1" gibi sayısal
        // seçimleri
        // doğru Enum değerine çevirecek şekilde tasarlanmalıdır.
        return DynamicController.convertInput(input, field.getType());
    }

    private Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(
                "No field named '" + fieldName + "' found in class " + clazz.getName() + " or its superclasses.");
    }

}