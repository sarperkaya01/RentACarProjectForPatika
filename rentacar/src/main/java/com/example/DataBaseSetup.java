package com.example;

import java.io.IOException;

public class DataBaseSetup {
    
    static void createDb(){
        try {
            // .sh file path
            String scriptPath = "/Users/sarperkaya/Desktop/RentACarProjectForPatika/rentacar/src/main/resources/setup_db.sh";

            // run scripti 
            ProcessBuilder pb = new ProcessBuilder("bash", scriptPath);
            pb.inheritIO(); // shows Scripts output in console
            Process process = pb.start();

            // wait till script process done
            int exitCode = process.waitFor();
            System.out.println("Script finished with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
