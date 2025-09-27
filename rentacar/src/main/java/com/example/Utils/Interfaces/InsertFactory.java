package com.example.Utils.Interfaces;

import java.lang.reflect.Field;
import java.util.Optional;

public interface InsertFactory<T, D> extends DynamicController {
    /**
     * Kullanıcıdan adım adım veri alıp, entity'yi oluşturup, servisi çağırarak
     * veritabanına kaydeden ve kaydedilmiş entity'yi döndüren süreç.
     * 
     * @return Veritabanına kaydedilmiş olan yeni entity.
     * @throws Exception Veri toplama veya kaydetme sırasında oluşabilecek hatalar.
     */
    T performInsertionProcess() throws Exception;

    /**
     * Kaydedilen entity'nin DTO'sunu bulmak için kullanılan metot.
     * 
     * @param identifier Genellikle plateOrTailNumber gibi tekil bir kimlik.
     * @return İlgili DTO'yu içeren bir Optional.
     */
    Optional<D> getDtoByIdentifier(String identifier);

    default void runInsertMenu() {
        System.out.println("\n--- Inserting New " + getEntitySimpleName() + " ---");
        try {
            // Asıl veri toplama ve kaydetme sürecini başlat.
            T savedEntity = performInsertionProcess();
            
            // Kaydedilen entity'den kimlik bilgisini (plaka vb.) al.
            String identifier = getIdentifier(savedEntity);

            System.out.println("\n--- SUCCESS ---");
            System.out.println("New " + getEntitySimpleName() + " has been saved successfully!");
            
            // Kimlik bilgisiyle DTO'yu bul ve kullanıcıya göster.
            Optional<D> dto = getDtoByIdentifier(identifier);
            dto.ifPresent(System.out::println);

        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR --- \nInvalid number format. Please check your inputs. Operation cancelled.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n--- ERROR --- \nInvalid input. " + e.getMessage() + ". Operation cancelled.");
        } catch (Exception e) {
            System.out.println("\n--- ERROR --- \nAn unexpected error occurred: " + e.getMessage());
        }
    }

    // 3. YARDIMCI METOTLAR: Arayüzün kendi içinde kullandığı özel metotlar.

    private String getEntitySimpleName() {
        String className = this.getClass().getSimpleName().replace("InsertController", "");
        return className;
    }

    private String getIdentifier(T entity) throws Exception {
        // Kaydedilen entity'den "plateOrTailNumber" alanını reflection ile okur.
        try {
            Field field = entity.getClass().getSuperclass().getDeclaredField("plateOrTailNumber");
            field.setAccessible(true);
            return (String) field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Exception("Could not retrieve identifier from the saved entity.");
        }
    }
}
