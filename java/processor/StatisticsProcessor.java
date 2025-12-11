package processor;

import data.DataStore;
import data.parking.ParkingViolation;
import data.properties.Property;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsProcessor {

    private final DataStore dataStore;
    private final MemoizationCache cache;

    public StatisticsProcessor(DataStore dataStore, MemoizationCache cache) {
        this.dataStore = dataStore;
        this.cache = cache;
    }

    public int getTotalPopulation() {
        if (cache.has("totalPopulation")) {
            return cache.getInt("totalPopulation");
        }
        int total = dataStore.getPopulationMap().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        cache.put("totalPopulation", total);
        return total;
    }

    public Map<String, Double> getFinesPerCapita() {
        if (cache.has("finesPerCapita")) {
            return cache.getMap("finesPerCapita");
        }

        Map<String, Integer> population = dataStore.getPopulationMap();
        Map<String, Integer> totalFines = new HashMap<>();

        for (ParkingViolation pv : dataStore.getParkingViolations()) {
            String zip = pv.getZipCode();
            if (zip == null || zip.isEmpty()) continue;
            if (!pv.getState().equalsIgnoreCase("PA")) continue;
            totalFines.put(zip, totalFines.getOrDefault(zip, 0) + pv.getFine());
        }

        Map<String, Double> result = new TreeMap<>();

        for (String zip : totalFines.keySet()) {
            if (!population.containsKey(zip)) continue;
            int pop = population.get(zip);
            if (pop <= 0) continue;
            double value = (double) totalFines.get(zip) / pop;
            double rounded = Math.round(value * 10000.0) / 10000.0;
            result.put(zip, rounded);
        }

        cache.put("finesPerCapita", result);
        return result;
    }

    public int getAverageMarketValue(String zip) {
        String key = "avgMarketValue:" + zip;
        if (cache.has(key)) return cache.getInt(key);

        List<Property> props = dataStore.getProperties();

        List<Double> valid = props.stream()
                .filter(p -> p.getZipCode().equals(zip))
                .map(Property::getMarketValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (valid.isEmpty()) {
            cache.put(key, 0);
            return 0;
        }

        double avg = valid.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        int result = (int) Math.round(avg);

        cache.put(key, result);
        return result;
    }

    public int getAverageLivableArea(String zip) {
        String key = "avgLivableArea:" + zip;
        if (cache.has(key)) return cache.getInt(key);

        List<Property> props = dataStore.getProperties();

        List<Double> valid = props.stream()
                .filter(p -> p.getZipCode().equals(zip))
                .map(Property::getTotalLivableArea)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (valid.isEmpty()) {
            cache.put(key, 0);
            return 0;
        }

        double avg = valid.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        int result = (int) Math.round(avg);

        cache.put(key, result);
        return result;
    }

    public int getMarketValuePerCapita(String zip) {
        String key = "mvPerCapita:" + zip;
        if (cache.has(key)) return cache.getInt(key);

        int pop = dataStore.getPopulationMap().getOrDefault(zip, 0);
        if (pop <= 0) {
            cache.put(key, 0);
            return 0;
        }

        double sum = dataStore.getProperties().stream()
                .filter(p -> p.getZipCode().equals(zip))
                .map(Property::getMarketValue)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (sum <= 0) {
            cache.put(key, 0);
            return 0;
        }

        int result = (int) Math.round(sum / pop);
        cache.put(key, result);
        return result;
    }
}
