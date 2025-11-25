package data.population;

import java.io.*;
import java.util.*;

public class PopulationReader {

    /**
     * Reads a whitespace-separated population file.
     * Each line: ZIP_CODE <space> POPULATION
     *
     * Returns a Map<String, Integer> so the processor tier
     * can efficiently look up population per ZIP.
     */
    public Map<String, Integer> read(String filename) throws IOException {

        Map<String, Integer> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                // Must be exactly ZIP + population
                if (parts.length != 2) continue;

                String zip = parts[0];
                int population = Integer.parseInt(parts[1]);

                map.put(zip, population);
            }
        }

        return map;
    }
}
