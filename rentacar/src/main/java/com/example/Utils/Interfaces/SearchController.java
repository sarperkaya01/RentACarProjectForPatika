package com.example.Utils.Interfaces;

import com.example.Utils.Global;
import com.example.Utils.Action.MenuAction;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Herhangi bir Entity (T) ve ona hizmet eden Repository/DAO (R) için
 * dinamik bir arama menüsü oluşturan jenerik bir arayüzdür.
 * @param <T> Aranacak olan Entity tipi (örn: Automobile).
 * @param <R> Arama metotlarını ('findBy...') içeren Repository/DAO tipi (örn: AutomobileDao).
 */
public interface SearchController<T, R> {

    // /**
    //  * Arayüzü implemente eden sınıfı, menünün kullanacağı
    //  * Repository/DAO nesnesini sağlamaya zorlar.
    //  */
    // R getRepository();

    // /**
    //  * Otomatik arama menüsünü başlatan ana metottur.
    //  */
    // default void runSearchMenu(Class<T> entityType) {
    //     Map<String, Function<Object, Object>> fieldSearchers = generateFieldSearchers(entityType);
    //     List<MenuAction> menuActions = generateSearchActions(entityType, fieldSearchers);

    //     while (true) {
    //         System.out.println("\n--- Search " + entityType.getSimpleName() + " by Field ---");
    //         for (int i = 0; i < menuActions.size(); i++) {
    //             System.out.println((i + 1) + ". " + menuActions.get(i).getDisplayName());
    //         }
    //         System.out.println((menuActions.size() + 1) + ". Back");

    //         System.out.print("Enter your choice: ");
    //         int choice;
    //         try {
    //             choice = Integer.parseInt(Global.scanner.nextLine());
    //         } catch (NumberFormatException e) {
    //             System.out.println("Invalid input. Please enter a number.");
    //             continue;
    //         }

    //         if (choice == menuActions.size() + 1) break;

    //         if (choice > 0 && choice <= menuActions.size()) {
    //             menuActions.get(choice - 1).getAction().run();
    //         } else {
    //             System.out.println("Invalid choice. Please try again.");
    //         }
    //     }
    // }

    // /**
    //  * Entity ve Repository/DAO arasında otomatik olarak bir "arama haritası" oluşturur.
    //  * Örneğin, 'plate' alanını 'findByPlate' metoduna bağlar.
    //  */
    // default Map<String, Function<Object, Object>> generateFieldSearchers(Class<T> entityType) {
    //     R repository = getRepository();
    //     Map<String, Function<Object, Object>> searchers = new HashMap<>();
    //     List<Field> fields = DynamicController.getAllFields(entityType);

    //     for (Field field : fields) {
    //         String fieldName = field.getName();
    //         try {
    //             String methodName = "findBy" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    //             Method searchMethod = repository.getClass().getMethod(methodName, field.getType());
    //             Function<Object, Object> searcher = (value) -> {
    //                 try {
    //                     return searchMethod.invoke(repository, value);
    //                 } catch (Exception e) {
    //                     throw new RuntimeException("Error executing search method: " + methodName, e);
    //                 }
    //             };
    //             searchers.put(fieldName, searcher);
    //         } catch (NoSuchMethodException e) {
    //             // Bu alan için bir 'findBy' metodu yoksa sessizce atla.
    //         }
    //     }
    //     return searchers;
    // }

    // /**
    //  * Oluşturulan arama haritasını kullanarak menüde gösterilecek aksiyonları üretir.
    //  */
    // private List<MenuAction> generateSearchActions(Class<T> entityType, Map<String, Function<Object, Object>> fieldSearchers) {
    //     List<MenuAction> actions = new ArrayList<>();
    //     List<Field> fields = DynamicController.getAllFields(entityType);

    //     for (Field field : fields) {
    //         String fieldName = field.getName();
    //         if (fieldSearchers.containsKey(fieldName)) {
    //             String displayName = "Search by " + DynamicController.formatFieldName(fieldName);
    //             Runnable action = createSearchAction(field, fieldSearchers.get(fieldName));
    //             actions.add(new MenuAction(displayName, action));
    //         }
    //     }
    //     return actions;
    // }

    // /**
    //  * Menüdeki tek bir arama seçeneği ("Search by Plate" gibi) için 'Runnable' komutunu oluşturur.
    //  */
    // private Runnable createSearchAction(Field field, Function<Object, Object> searcher) {
    //     return () -> {
    //         try {
    //             if (field.getType().isEnum()) {
    //                 System.out.println("Please select a value for " + DynamicController.formatFieldName(field.getName()) + ":");
    //                 Object[] enumConstants = field.getType().getEnumConstants();
    //                 for (int i = 0; i < enumConstants.length; i++) {
    //                     System.out.println((i + 1) + ". " + enumConstants[i].toString());
    //                 }
    //                 System.out.print("Enter your choice (number): ");
    //             } else {
    //                 System.out.print("Enter value for " + DynamicController.formatFieldName(field.getName()) + " to search: ");
    //             }

    //             String input = Global.scanner.nextLine();
    //             Object convertedValue = DynamicController.convertInput(input, field.getType());
    //             Object result = searcher.apply(convertedValue);
                
    //             printResults(result);

    //         } catch (Exception e) {
    //             System.out.println("Error during search: " + e.getMessage());
    //         }
    //     };
    // }

    // /**
    //  * Arama sonuçlarını (ister Liste, ister Optional olsun) formatlı bir şekilde ekrana basar.
    //  */
    // private void printResults(Object result) {
    //     System.out.println("\n--- Search Results ---");
    //     if (result instanceof List) {
    //         List<?> resultList = (List<?>) result;
    //         if (resultList.isEmpty()) {
    //             System.out.println("No records found.");
    //         } else {
    //             resultList.forEach(System.out::println);
    //         }
    //     } else if (result instanceof Optional) {
    //         Optional<?> optionalResult = (Optional<?>) result;
    //         System.out.println(optionalResult.orElse("No record found."));
    //     } else if (result != null) {
    //         System.out.println(result);
    //     } else {
    //          System.out.println("No records found.");
    //     }
    //     System.out.println("----------------------");
    // }
}