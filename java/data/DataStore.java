package data;

import data.parking.*;
import data.properties.*;
import data.population.*;

import java.util.*;

public class DataStore {

    private List<ParkingViolation> parkingViolations;
    private List<Property> properties;
    private Map<String, Integer> population;

    //Loads parking data based on format ("csv" or "json").
    public void loadParkingData(String format, String filename) throws Exception {
        if (format.equals("csv")) {
            parkingViolations = new ParkingCSVReader().read(filename);
        }
        else if (format.equals("json")) {
            parkingViolations = new ParkingJSONReader().read(filename);
        }
        else {
            throw new IllegalArgumentException("Invalid parking data format: " + format);
        }
    }

    //Loads the properties CSV file./
    public void loadProperties(String filename) throws Exception {
        properties = new PropertyCSVReader().read(filename);
    }

    //Loads the ZIP code population file.
    public void loadPopulation(String filename) throws Exception {
        population = new PopulationReader().read(filename);
    }

    //Getters used by processing tier

    public List<ParkingViolation> getParkingViolations() {
        return parkingViolations;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public Map<String, Integer> getPopulationMap() {
        return population;
    }
}
