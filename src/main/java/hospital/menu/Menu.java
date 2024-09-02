package hospital.menu;

import hospital.Hospital;
import hospital.Person;
import hospital.Room;
import hospital.enums.TypeOfFunction;

import java.io.IOException;
import java.time.LocalDateTime;

import static hospital.io.JsonHandler.exportEvents;
import static hospital.io.JsonHandler.exportPeople;
import static hospital.menu.ReadInfo.manageAccess;
import static hospital.menu.Tools.getInt;
import static hospital.menu.Tools.getLocalDateTime;

/**
 * Class that represents the menu of the application
 * It has the main menu and the submenus
 */
public class Menu {
    /**
     * Main menu
     *
     * @param hospital the hospital
     * @throws IOException if the input is invalid
     */
    public static void mainMenu(Hospital hospital) throws IOException {
        boolean isRunning = true;

        while (isRunning) {
            //Show the main menu
            System.out.println(Display.displayMainMenu());

            int choice = getInt();
            int id;
            switch (choice) {
                case 1:
                    roomsMenu(hospital);
                    break;
                case 2:
                    individualsManagement(hospital);
                    break;
                case 3:
                    registryMenu(hospital);
                    break;
                case 4:
                    contactMenu(hospital);
                    break;
                case 5:
                    System.out.println("Enter individual ID: ");
                    id = getInt();
                    while (hospital.getPersonById(id) == null) {
                        System.out.println("Person not found.");
                        System.out.println("Enter individual ID: ");
                        id = getInt();
                    }
                    Person person = hospital.getPersonById(id);
                    System.out.println(person.getLocation());
                    break;
                case 6:
                    System.out.println("Choose the room to where you are: ");
                    id = getInt();
                    while (hospital.getRoomById(id) == null) {
                        System.out.println("Room not found.");
                        System.out.println("Enter room ID: ");
                        id = getInt();
                    }
                    hospital.findClosestExit(id);
                    break;
                case 7:
                    hospital.printVisualMap();
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }

    /**
     * Rooms menu
     *
     * @param hospital
     * @throws IOException
     */
    private static void roomsMenu(Hospital hospital) throws IOException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(Display.roomMenu());
            int choice = getInt();
            switch (choice) {
                /*
                case 1:
                    System.out.println("New room");
                    hospital.addRoom(readRoom());
                    break;
                case 1:
                    System.out.println("JsonHandler room");
                    //TODO
                    break;*/
                case 1:
                    System.out.println("See all rooms");
                    System.out.println(hospital.getRooms());
                    break;
                case 2:
                    System.out.println("Choose a room to manage access: ");
                    int id = getInt();
                    while (hospital.getRoomById(id) == null) {
                        System.out.println("Room not found.");
                        System.out.println("Enter room ID: ");
                        id = getInt();
                    }
                    Room room = hospital.getRoomById(id);
                    System.out.println(room);


                    System.out.println(Display.displayAccess());

                    System.out.println("Choose an option: ");
                    int choice2 = getInt();
                    switch (choice2) {
                        case 1:
                            room.addAccess(manageAccess());
                            System.out.println(room);
                            break;
                        case 2:
                            room.revokeAccess();
                            System.out.println(room);
                            break;
                        case 0:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid option, please choose a valid option.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Choose a room to view activity: ");
                    id = getInt();
                    while (hospital.getRoomById(id) == null) {
                        System.out.println("Room not found.");
                        System.out.println("Enter room ID: ");
                        id = getInt();
                    }
                    room = hospital.getRoomById(id);
                    System.out.println(room);
                    System.out.println(room.getEvents());
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }

    /**
     * Individuals management menu
     *
     * @param hospital
     * @throws IOException
     */
    private static void individualsManagement(Hospital hospital) throws IOException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println(Display.displayIndividualsMenu());
            System.out.print("Choose an option: ");
            int choice = getInt();
            switch (choice) {
                case 1:
                    System.out.println("New individual");
                    String name = ReadInfo.readName();
                    int age = ReadInfo.readAge();

                    System.out.println("Enter the individual ID");
                    int id = getInt();
                    while (id < 100000000 || id > 999999999) {
                        System.out.println("Invalid ID, please enter a valid ID" +
                                "\t (9 digits)");
                        id = getInt();
                    }

                    System.out.println("Enter the individual function");
                    TypeOfFunction function = manageAccess();

                    hospital.addPerson(new Person(id, name, age, function, hospital.getRoomById(0)));
                    break;
                case 2:
                    exportPeople(hospital, "src/main/resources/people.json");
                    break;
                case 3:
                    System.out.println("See all individuals");
                    System.out.println(hospital.getPeople());
                    break;
                case 4:
                    System.out.println("Enter the individual ID");
                    id = getInt();
                    while (hospital.getPersonById(id) == null) {
                        System.out.println("Person not found.");
                        System.out.println("Enter individual ID: ");
                        id = getInt();
                    }
                    System.out.println(hospital.getPersonById(id).getActivity());
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }

    /**
     * Registry menu
     *
     * @param hospital
     * @throws IOException
     */
    private static void registryMenu(Hospital hospital) throws IOException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.print(Display.displayRegistryMenu());
            int choice = getInt();
            switch (choice) {
                case 1:
                    hospital.addEvent(hospital.readEvent());
                    break;
                case 2:
                    exportEvents(hospital, "src/main/resources/events.json");
                    break;
                case 3:
                    System.out.println(hospital.getEvents());
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }

    /**
     * Contact with people or rooms menu
     *
     * @param hospital
     * @throws IOException
     */
    private static void contactMenu(Hospital hospital) throws IOException {
        boolean isRunning = true;
        LocalDateTime from;
        LocalDateTime to;
        while (isRunning) {
            System.out.println(Display.displayContactMenu());
            int choice = getInt();
            int id;
            switch (choice) {
                case 1:
                    System.out.println("Enter individual ID: ");
                    id = getInt();
                    while (hospital.getPersonById(id) == null) {
                        System.out.println("Person not found.");
                        System.out.println("Enter individual ID: ");
                        id = getInt();
                    }

                    System.out.println("Enter the time of start: ");
                    from = getLocalDateTime();

                    System.out.println("Enter the time of end: ");
                    to = getLocalDateTime();

                    System.out.println(hospital.hadContactWithIndividual(id, from, to));
                    break;
                case 2:
                    System.out.println("Enter room ID: ");
                    id = getInt();
                    while (hospital.getRoomById(id) == null) {
                        System.out.println("Room not found.");
                        System.out.println("Enter room ID: ");
                        id = getInt();
                    }

                    System.out.println("Enter the time of start: ");
                    from = getLocalDateTime();

                    System.out.println("Enter the time of end: ");
                    to = getLocalDateTime();

                    System.out.println(hospital.hadContactWithRoom(id, from, to));
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please choose a valid option.");
                    break;
            }
        }
    }
}