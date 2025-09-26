package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DynamicController {
    /**
     * Bir sınıfın ve onun tüm üst sınıflarının alanlarını (field)
     * toplayıp bir liste olarak döndürür.
     */
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * camelCase formatındaki bir alan adını (örn: "brandName") kullanıcıya gösterilecek
     * daha okunaklı bir formata (örn: "Brand Name") çevirir.
     */
    public static String formatFieldName(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) return "";
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

    /**
     * KULLANICI DOSTU ENUM İŞLEME YETENEĞİNE SAHİP GELİŞMİŞ VERSİYON
     * Kullanıcıdan String olarak alınan girdiyi, ilgili alanın gerçek Java tipine dönüştürür.
     * Eğer tip bir Enum ise, kullanıcıdan gelen sayısal seçimi (örn: "1")
     * doğru Enum sabitine (örn: AVAILABLE) çevirir.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) // Enum.valueOf için güvenli cast
    public static Object convertInput(String input, Class<?> targetType) {
        if (targetType.isEnum()) {
            try {
                // Girdinin sayı olup olmadığını kontrol et
                int choice = Integer.parseInt(input) - 1; // 1 tabanlı indeksi 0 tabanlıya çevir
                Object[] enumConstants = targetType.getEnumConstants();
                if (choice >= 0 && choice < enumConstants.length) {
                    // Geçerli bir sayı ise, o indeksteki enum sabitini döndür
                    return enumConstants[choice];
                } else {
                    // Eğer sayı aralık dışındaysa, geçersiz olduğunu belirt
                    throw new IllegalArgumentException("Invalid number choice. Please select a number between 1 and " + enumConstants.length);
                }
            } catch (NumberFormatException e) {
                // Eğer sayı değilse, metin olarak eşleştirmeyi dene (büyük harfe çevirerek)
                return Enum.valueOf((Class<Enum>) targetType, input.toUpperCase());
            }
        }
        
        // Diğer tipler için standart dönüşüm
        if (targetType.equals(String.class)) return input;
        if (targetType.equals(Integer.class)) return Integer.parseInt(input);
        if (targetType.equals(BigDecimal.class)) return new BigDecimal(input);
        
        throw new IllegalArgumentException("Unsupported field type for conversion: " + targetType.getSimpleName());
    }

}
