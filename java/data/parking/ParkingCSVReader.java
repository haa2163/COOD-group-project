package data.parking;

import java.io.*;
import java.util.*;

public class ParkingCSVReader {

    public List<ParkingViolation> read(String filename) throws IOException {
        List<ParkingViolation> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // CSV is guaranteed to have exactly 7 fields per row
                if (parts.length != 7) continue;

                String dateTime = parts[0];
                int fine = Integer.parseInt(parts[1]);
                String violation = parts[2];
                String plateId = parts[3];
                String state = parts[4];
                String ticketNumber = parts[5];
                String zipCode = parts[6];  // may be empty

                list.add(new ParkingViolation(
                        ticketNumber,
                        plateId,
                        state,
                        dateTime,
                        violation,
                        fine,
                        zipCode
                ));
            }
        }

        return list;
    }
}
