package hospital.io;

import collections.graphs.Network;
import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import collections.lists.arrayLists.ArrayUnorderedList;
import hospital.Event;
import hospital.Hospital;
import hospital.Person;
import hospital.Room;
import hospital.enums.TypeOfFunction;
import hospital.enums.TypeOfRoom;
import hospital.exceptions.ImportException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * The JsonHandler class provides methods to import and export data from and to JSON files.
 */
public class JsonHandler {

    /**
     * Imports room data from a JSON file and adds the rooms to the hospital.
     *
     * @param filename The name of the file to import
     *
     * @return The list of rooms
     */
    public static UnorderedListADT<Room> importRooms(String filename) {
        UnorderedListADT<Room> rooms = new ArrayUnorderedList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {

            Object obj = parser.parse(reader);

            JSONArray roomList = (JSONArray) obj;

            for (Object roomObj : roomList) {
                JSONObject roomJson = (JSONObject) roomObj;

                int id = ((Long) roomJson.get("id")).intValue();
                String name = (String) roomJson.get("name");
                String typeStr = (String) roomJson.get("type");
                TypeOfRoom type = TypeOfRoom.valueOf(typeStr.toUpperCase());
                int capacity = ((Long) roomJson.get("capacity")).intValue();
                int currentOccupation = ((Long) roomJson.get("currentOccupation")).intValue();
                boolean occupied = (Boolean) roomJson.get("occupied");

                JSONArray accessArray = (JSONArray) roomJson.get("access");
                UnorderedListADT<TypeOfFunction> access = new UnorderedLinkedList<>();
                for (Object accessObj : accessArray) {
                    String accessStr = (String) accessObj;
                    access.addToRear(TypeOfFunction.valueOf(accessStr.toUpperCase()));
                }

                Room room = new Room(id, access, currentOccupation, occupied, capacity, type, name);
                // hospital.getHospitalMap().addVertex(room);
                rooms.addToRear(room);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (rooms.isEmpty()) {
            System.out.println("No rooms found in the file");
        }

        return rooms;
    }

    /**
     * Exports people data to a JSON file.
     *
     * @param hospital The hospital from where you'll be export
     * @param filename The name of the file to export
     */
    public static void exportPeople(Hospital hospital, String filename) {
        JSONArray peopleArray = new JSONArray();

        Iterator<Person> peopleIterator = hospital.getAllPeople().iterator();

        while (peopleIterator.hasNext()) {
            Person person = peopleIterator.next();
            JSONObject personObject = new JSONObject();
            personObject.put("id", person.getId());
            personObject.put("name", person.getName());
            personObject.put("age", person.getAge());
            personObject.put("function", person.getFunction().toString());

            peopleArray.add(personObject);
        }

        // Write JSON file
        try (FileWriter file = new FileWriter(filename)) {
            file.write(peopleArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports people data from a JSON file and adds the people to the hospital.
     *
     * @param filename The name of the file to import
     *
     * @return The list of people
     */
    public static UnorderedListADT<Person> importPeople(String filename) {
        UnorderedListADT<Person> people = new UnorderedLinkedList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            JSONArray peopleArray = (JSONArray) jsonParser.parse(reader);
            for (Object obj : peopleArray) {
                JSONObject personObject = (JSONObject) obj;
                int id = ((Long) personObject.get("id")).intValue();
                String name = (String) personObject.get("name");
                int age = ((Long) personObject.get("age")).intValue();
                String functionStr = ((String) personObject.get("function")).toUpperCase();
                TypeOfFunction function = TypeOfFunction.valueOf(functionStr); // Convert to uppercase
                // Create a Person object
                Person person = new Person(id, name, age, function);
                people.addToRear(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return people;
    }

    /**
     * Import events data from a JSON file and add the events to the hospital.
     *
     * @param hospital The hospital where you'll be importing
     * @param filename The name of the file to import
     */
    public static void importEvents(Hospital hospital, String filename) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {

            JSONArray eventsArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : eventsArray) {
                JSONObject eventObject = (JSONObject) obj;

                int personId = ((Long) eventObject.get("personId")).intValue();
                int fromRoomId = ((Long) eventObject.get("fromRoomId")).intValue();
                int toRoomId = ((Long) eventObject.get("toRoomId")).intValue();
                String timeStr = (String) eventObject.get("time");

                LocalDateTime time = LocalDateTime.parse(timeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Person person = hospital.getPersonById(personId);
                Room fromRoom = hospital.getRoomById(fromRoomId);
                Room toRoom = hospital.getRoomById(toRoomId);

                if (person == null) {
                    System.err.println("Warning: Person with ID " + personId + " not found. Skipping event.");
                    continue;
                }

                if (fromRoom == null) {
                    System.err.println("Warning: From Room with ID " + fromRoomId + " not found. Skipping event.");
                    continue;
                }

                if (toRoom == null) {
                    System.err.println("Warning: To Room with ID " + toRoomId + " not found. Skipping event.");
                    continue;
                }

                // Create an Event object
                Event event = new Event(person, fromRoom, toRoom, time);

                // Add the Event to the Person
                person.getActivity().addToRear(event);

                // Add the Event to the Hospital
                toRoom.addEvent(event);

                //remove person from the room he/she came
                if (!fromRoom.getPeopleInRoom().isEmpty()) {
                    if (fromRoom.getPeopleInRoom().contains(person))
                        fromRoom.removePerson(person);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing event data: " + e.getMessage());
        }
    }

    /**
     * Export events data to a JSON file.
     *
     * @param hospital The hospital from where you'll be exporting
     * @param filename The name of the file to export
     */
    public static void exportEvents(Hospital hospital, String filename) {
        JSONArray eventsArray = new JSONArray();
        UnorderedListADT<Room> allRooms = hospital.getAllRooms();

        Iterator<Room> roomIterator = allRooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            Iterator<Event> eventIterator = room.getEvents().iterator();
            while (eventIterator.hasNext()) {
                Event event = eventIterator.next();
                JSONObject eventObject = new JSONObject();
                eventObject.put("personId", event.getPerson().getId());
                eventObject.put("fromRoomId", event.getFrom().getId());
                eventObject.put("toRoomId", event.getTo().getId());
                eventObject.put("time", event.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                eventsArray.add(eventObject);
            }
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write(eventsArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports map data as form of edges from a JSON file and adds the edges to the hospital.
     *
     * @param filename The name of the file to import
     * @return The network of the hospital
     * @throws FileNotFoundException If it not finds the file
     * @throws ImportException       If there is an error reading the file or parsing the JSON content
     */
    public static Network<Room> importMap(String filename) throws FileNotFoundException, ImportException {

        Iterator<Room> roomIterator = importRooms("src/main/resources/rooms.json").iterator();
        Iterator<Person> peopleIterator = importPeople("src/main/resources/people.json").iterator();

        Network<Room> network = new Network<>();
        JSONParser jsonParser = new JSONParser();
        Room room1 = new Room();
        room1.setId(-1);
        Room room2 = new Room();
        room2.setId(-1);

        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            network.addVertex(room);
        }

        while (peopleIterator.hasNext()) {
            Person person = peopleIterator.next();
            network.getVertex(0).addPerson(person);
        }

        try (FileReader fileReader = new FileReader(filename)) {
            JSONArray edgesArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object obj : edgesArray) {
                JSONObject edgeObject = (JSONObject) obj;
                int roomId1 = ((Long) edgeObject.get("room1")).intValue();
                int roomId2 = ((Long) edgeObject.get("room2")).intValue();

                Iterator<Room> newRoomIterator = importRooms("src/main/resources/rooms.json").iterator();

                while (newRoomIterator.hasNext() && room1.getId() != roomId1) {
                    room1 = newRoomIterator.next();
                }
                while (newRoomIterator.hasNext() && room2.getId() != roomId2) {
                    room2 = newRoomIterator.next();
                }
                double weight = ((Long) edgeObject.get("weight")).doubleValue();
                network.addEdge(room1, room2, weight);
                network.addEdge(room2, room1, weight);
            }

        } catch (IOException e) {
            // Wrap IOException in a custom exception with a meaningful message
            throw new ImportException("Error reading the file: " + filename, e);
        } catch (ParseException e) {
            // Wrap ParseException in a custom exception with a meaningful message
            throw new ImportException("Error parsing JSON content from the file: " + filename, e);
        }
        return network;
    }

}


