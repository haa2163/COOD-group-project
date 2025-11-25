package data.parking;

import java.io.FileReader;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class ParkingJSONReader {

    public List<ParkingViolation> read(String filename) throws Exception {

        List<ParkingViolation> list = new ArrayList<>();
        JSONParser parser = new JSONParser();

        JSONArray array = (JSONArray) parser.parse(new FileReader(filename));

        for (Object obj : array) {
            JSONObject json = (JSONObject) obj;

            String ticketNumber = String.valueOf(json.get("ticket_number"));
            String plateId = String.valueOf(json.get("plate_id"));
            String state = String.valueOf(json.get("state"));
            String dateTime = String.valueOf(json.get("date"));
            String violation = String.valueOf(json.get("violation"));
            int fine = ((Long) json.get("fine")).intValue();
            String zipCode = String.valueOf(json.get("zip_code"));

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

        return list;
    }
}
