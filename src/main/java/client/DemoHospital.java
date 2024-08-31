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
        /*
        LocalDateTime now = LocalDateTime.now();
        Person p1 = new Person(234567891, "John", 20, TypeOfFunction.PATIENT);
        Room r1 = new Room("Principal Canteen", TypeOfRoom.CANTEEN, 25);
        Room r2 = new Room("Block 2 Operating Room", TypeOfRoom.OPERATING_ROOM, 4);

        Event e1 = new Event(p1, r1, r2, now);
        hospital.addRoom(r1);
        hospital.addRoom(r2);
        hospital.addPerson(p1);
        hospital.addEvent(e1);
        hospital.addEvent(e1);
        System.out.println(hospital.getPeople());
        System.out.println(hospital.getRooms());
        System.out.println(e1);
        System.out.println(hospital.getPeople());
         */
        importRooms(hospital, "src/main/resources/rooms.json");
        importPeople(hospital, "src/main/resources/people.json");
        importEvents(hospital, "src/main/resources/events.json");

        try {
            hospital.setHospitalMap((importMap(hospital, "src/main/resources/map.json")));
            Menu.mainMenu(hospital);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ImportException e) {
            throw new RuntimeException(e);
        }
    }
}
