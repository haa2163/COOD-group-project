package data.properties;

import java.io.*;
import java.util.*;

public class PropertyCSVReader {

    public List<Property> read(String filename) throws IOException {

        List<Property> properties = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            // Read header row (must exist)
            String header = br.readLine();
            if (header == null) return properties;

            String[] cols = header.split(",");
            Map<String, Integer> index = new HashMap<>();

            // Map column names → index positions
            for (int i = 0; i < cols.length; i++) {
                index.put(cols[i], i);
            }

            // Required columns (spec guarantees these exist)
            int idxMarketValue = index.get("market_value");
            int idxLivableArea = index.get("total_livable_area");
            int idxZip = index.get("zip_code");

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length < cols.length) continue;

                // ZIP: first 5 digits only
                String rawZip = parts[idxZip];
                if (rawZip.length() < 5) continue;
                String zip = rawZip.substring(0, 5);

                // Validate numeric values
                Double marketValue = parseNumeric(parts[idxMarketValue]);
                Double livableArea = parseNumeric(parts[idxLivableArea]);

                properties.add(new Property(zip, marketValue, livableArea));
            }
        }

        return properties;
    }

    /**
     * Parses a numeric string safely.
     * Returns null for:
     * - missing values
     * - non-numeric values
     * - negative or zero values 
     */
    private Double parseNumeric(String s) {
        try {
            double val = Double.parseDouble(s);
            return val > 0 ? val : null;
        } catch (Exception e) {
            return null;
        }
    }
}
