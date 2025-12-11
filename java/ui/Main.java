package ui;

import data.DataStore;
import processor.StatisticsProcessor;
import processor.MemoizationCache;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: <csv|json> <parking file> <properties file> <population file>");
            return;
        }

        String format = args[0];
        String parkingFile = args[1];
        String propertiesFile = args[2];
        String populationFile = args[3];

        if (!format.equals("csv") && !format.equals("json")) {
            System.out.println("Invalid parking data format. Must be csv or json.");
            return;
        }

        if (!new File(parkingFile).exists() ||
            !new File(propertiesFile).exists() ||
            !new File(populationFile).exists()) {
            System.out.println("One or more input files do not exist.");
            return;
        }

        DataStore ds = new DataStore();
        try {
            ds.loadParkingData(format, parkingFile);
            ds.loadProperties(propertiesFile);
            ds.loadPopulation(populationFile);
        } catch (Exception e) {
            System.out.println("Error loading data files.");
            return;
        }

        MemoizationCache cache = MemoizationCache.getInstance();
        StatisticsProcessor processor = new StatisticsProcessor(ds, cache);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1: Total Population");
            System.out.println("2: Fines Per Capita");
            System.out.println("3: Average Market Value");
            System.out.println("4: Average Total Livable Area");
            System.out.println("5: Residential Market Value Per Capita");
            System.out.println("0: Quit Program");

            String choice = scanner.nextLine();

            if (choice.equals("0")) break;

            if (choice.equals("1")) {
                int total = processor.getTotalPopulation();
                System.out.println(total);
            }

            else if (choice.equals("2")) {
                Map<String, Double> results = processor.getFinesPerCapita();
                for (String zip : results.keySet()) {
                    System.out.println(zip + ": " + String.format("%.4f", results.get(zip)));
                }
            }

            else if (choice.equals("3")) {
                System.out.println("Enter a ZIP Code:");
                String zip = scanner.nextLine();
                int avg = processor.getAverageMarketValue(zip);
                System.out.println(avg);
            }

            else if (choice.equals("4")) {
                System.out.println("Enter a ZIP Code:");
                String zip = scanner.nextLine();
                int avg = processor.getAverageLivableArea(zip);
                System.out.println(avg);
            }

            else if (choice.equals("5")) {
                System.out.println("Enter a ZIP Code:");
                String zip = scanner.nextLine();
                int value = processor.getMarketValuePerCapita(zip);
                System.out.println(value);
            }
        }

        scanner.close();
    }
}
