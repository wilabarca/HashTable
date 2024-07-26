import Models.TablaHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();


        TablaHash tablaHash1 = new TablaHash(1000);
        TablaHash tablaHash2 = new TablaHash(1000);


        String line;
        String splitBy = ",";
        int id = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("bussines.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                if (business.length >= 5) {
                    String key = business[1];
                    String value = "ID=" + business[0] + ", Dirección=" + business[2] +
                            ", Ciudad=" + business[3] + ", Estado=" + business[4];


                    tablaHash1.put(key, value, 1);
                    tablaHash2.put(key, value, 2);


                    System.out.println("[" + id + "] Negocio [ID=" + business[0] + ", Nombre=" +
                            business[1] + ", Dirección=" + business[2] + ", Ciudad=" +
                            business[3] + ", Estado=" + business[4] + "]");
                    id++;
                } else {
                    System.err.println("Saltando línea inválida: " + line);
                }
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese clave para buscar: ");
            String searchKey = scanner.nextLine().trim();


            long searchStartTime1 = System.nanoTime();
            List<Integer> foundIndices1 = tablaHash1.searchAndGetIndices(searchKey);
            Object dataFromTablaHash1 = tablaHash1.get(searchKey, 1);
            long searchEndTime1 = System.nanoTime();
            System.out.println("Tiempo de búsqueda en Tabla Hash 1: " + (searchEndTime1 - searchStartTime1) + " nanosegundos.");

            long searchStartTime2 = System.nanoTime();
            List<Integer> foundIndices2 = tablaHash2.searchAndGetIndices(searchKey);
            Object dataFromTablaHash2 = tablaHash2.get(searchKey, 2);
            long searchEndTime2 = System.nanoTime();
            System.out.println("Tiempo de búsqueda en Tabla Hash 2: " + (searchEndTime2 - searchStartTime2) + " nanosegundos.");


            if (dataFromTablaHash1 != null || dataFromTablaHash2 != null) {
                System.out.println("Datos encontrados para la clave '" + searchKey + "':");

                if (dataFromTablaHash1 != null) {
                    System.out.println("Datos en Tabla Hash 1:");
                    System.out.println(dataFromTablaHash1);
                } else {
                    System.out.println("Clave '" + searchKey + "' no encontrada en Tabla Hash 1.");
                }

                if (dataFromTablaHash2 != null) {
                    System.out.println("Datos en Tabla Hash 2:");
                    System.out.println(dataFromTablaHash2);
                } else {
                    System.out.println("Clave '" + searchKey + "' no encontrada en Tabla Hash 2.");
                }
            } else {
                System.out.println("Clave '" + searchKey + "' no encontrada en ninguna tabla hash.");
            }

            if (!foundIndices1.isEmpty() || !foundIndices2.isEmpty()) {
                System.out.print("Clave '" + searchKey + "' encontrada en los índices: ");
                for (int index : foundIndices1) {
                    System.out.print(index + " ");
                }
                for (int index : foundIndices2) {
                    System.out.print(index + " ");
                }
                System.out.println();
            } else {
                System.out.println("Clave '" + searchKey + "' no encontrada.");
            }


            int searchIndex = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Ingrese el índice para mostrar los datos: ");
                if (scanner.hasNextInt()) {
                    searchIndex = scanner.nextInt();
                    validInput = true;
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                    scanner.next();
                }
            }

            System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 1:");
            List<String> data1 = tablaHash1.getDataAtIndex(searchIndex);
            for (String data : data1) {
                System.out.println(data);
            }

            System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 2:");
            List<String> data2 = tablaHash2.getDataAtIndex(searchIndex);
            for (String data : data2) {
                System.out.println(data);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        System.out.println("Tiempo total de ejecución: " + (endTime - startTime) + " nanosegundos.");
    }
}
