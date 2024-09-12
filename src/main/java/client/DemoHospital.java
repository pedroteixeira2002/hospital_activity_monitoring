package client;

import hospital.Hospital;
import hospital.exceptions.ImportException;
import hospital.menu.Menu;

import java.io.IOException;

import static hospital.io.JsonHandler.*;

/**
 * This class is used to run the hospital application
 */
public abstract class DemoHospital {
    /**
     * The main method of the application
     *
     * @param args The arguments of the application
     */
    public static void main(String[] args) {

        Hospital hospital = new Hospital();
        try {
            hospital.setHospitalMap((importMap("src/main/resources/map.json")));
            importEvents(hospital, "src/main/resources/events.json");

            Menu.mainMenu(hospital);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ImportException e) {
            throw new RuntimeException(e);
        }
    }
}
