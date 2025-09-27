package com.example.Utils.Interfaces;

import java.util.List;
import java.util.function.Supplier;

public interface SummarizableController<D> {

    Supplier<List<D>> getSummaryDtoListSupplier();

    String getEntityName();

    default void listAllSummary() {
        System.out.println("\n--- All " + getEntityName() + "s in the System (Summary View) ---");

        List<D> summaryList = getSummaryDtoListSupplier().get();

        if (summaryList.isEmpty()) {
            System.out.println("No " + getEntityName().toLowerCase() + "s found in the system.");
        } else {
            summaryList.forEach(System.out::println);
        }
    }
}