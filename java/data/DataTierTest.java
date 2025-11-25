package data;

import data.parking.*;
import data.properties.*;
import data.population.*;

import java.util.*;

public class DataTierTest {

    public static void main(String[] args) {
        try {
            DataStore ds = new DataStore();

            ds.loadParkingData("csv", "data/parking.csv");
            ds.loadProperties("data/properties.csv");
            ds.loadPopulation("data/population.txt");

            System.out.println("=== DATA TIER TEST ===");

            System.out.println("Parking violations loaded: "
                    + ds.getParkingViolations().size());

            System.out.println("Properties loaded: "
                    + ds.getProperties().size());

            System.out.println("Population entries loaded: "
                    + ds.getPopulationMap().size());

            System.out.println("\nSample Parking Violation:");
            System.out.println(ds.getParkingViolations().get(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
