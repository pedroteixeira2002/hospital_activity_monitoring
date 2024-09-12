package hospital;

import collections.graphs.Network;
import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.enums.TypeOfFunction;
import hospital.enums.TypeOfRoom;
import hospital.menu.Tools;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Represents a hospital.
 * It manages a set of rooms, people, and events that occur in the hospital.
 * It also contains a map of the hospital and methods to interact with it.
 * The hospital detect contacts between people and rooms within a specified date range.
 * The hospital find the closest exit given a room.
 * The hospital print a visual representation of the map using a graphical interface.
 * The hospital detect all people who had contact with a room within a specified date range.
 * The hospital detect all people who had contact with a person within a specified date range.
 */
public class Hospital {
    /**
     * The hospital map.
     */
    private Network<Room> hospitalMap;

    /**
     * Default constructor that initializes the hospital with an empty map, rooms, people, and events.
     * It also initializes the list of edges.
     */
    public Hospital() {
        this.hospitalMap = new Network<>();
    }

    /**
     * Sets the hospital map.
     *
     * @param hospitalMap The hospital map to set.
     */
    public void setHospitalMap(Network<Room> hospitalMap) {
        this.hospitalMap = hospitalMap;
    }

    /**
     * Searches for a room by its ID in the hospital map.
     *
     * @param roomId The ID of the room to search for.
     * @return The room if found, null otherwise.
     */
    public Room getRoomById(int roomId) {
        Iterator<Room> roomIterator = hospitalMap.iteratorBFS(0);
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }

    /**
     * Searches for a person by their ID across all rooms in the hospital map.
     *
     * @param personId The ID of the person to search for.
     * @return The person if found, null otherwise.
     */
    public Person getPersonById(int personId) {
        Iterator<Room> roomIterator = hospitalMap.iteratorBFS(0); // Assuming BFS traversal
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            UnorderedListADT<Person> peopleInRoom = room.getPeopleInRoom();
            Iterator<Person> personIterator = peopleInRoom.iterator();
            while (personIterator.hasNext()) {
                Person person = personIterator.next();
                if (person.getId() == personId) {
                    return person;
                }
            }
        }
        return null;
    }

    /**
     * Prints a visual representation of the map using a graphical interface.
     */
    public void printVisualMap() {
        Graph graph = new SingleGraph("Map Visualization");

        // configure the visual representation of the graph
        graph.addAttribute("ui.stylesheet",
                "node { " +
                        "fill-color: red;" +
                        " size: 20px; text-size: 20; } " +
                        "edge { fill-color: grey;" +
                        " size: 2px;" +
                        " text-size: 12;" +
                        " text-color: black;" +
                        " text-background-mode: plain;" +
                        " text-background-color: white; }");

        int count = 0;
        while (count < hospitalMap.size()) {
            graph.addNode(Integer.toString(count));
            Node node = graph.getNode(Integer.toString(count));
            node.addAttribute("ui.label", Integer.toString(count));
            count++;
        }

        // add edges and respective weights
        for (int i = 0; i < hospitalMap.size(); i++) {
            Room room1 = hospitalMap.getVertex(i);
            for (int j = 0; j < hospitalMap.size(); j++) {
                Room room2 = hospitalMap.getVertex(j);
                if (hospitalMap.edgeExists(room1, room2) && graph.getEdge(i + "_" + j) == null) {
                    double weight = hospitalMap.getWeight(room1, room2);
                    String edgeId = i + "_" + j;
                    graph.addEdge(edgeId, Integer.toString(i), Integer.toString(j), true);
                    graph.getEdge(edgeId).addAttribute("ui.label", String.format("%.2f", weight));
                }
            }
        }

        graph.display();
    }

    /**
     * Identifies if someone who is trying to enter a room has permission to do so.
     *
     * @param person The person trying to enter the room.
     * @param room   The room the person is trying to enter.
     * @return True if the person has permission to enter the room, false otherwise.
     */
    private boolean hasPermission(Person person, Room room) {
        TypeOfFunction function = person.getFunction();
        return room.getAccess().contains(function);
    }

    /**
     * Reads the information needed to create an event.
     *
     * @return The event created.
     * @throws IOException If an I/O error occurs.
     */
    public Event readEvent() throws IOException {

        System.out.println("Enter Person ID:");
        int id = Tools.getInt();
        if (getPersonById(id) == null) {
            System.out.println("\nPerson not recognized, please contact support" +
                    "\nExiting event creation\n");
            return null;
        }
        Person person = getPersonById(id);

        Room from = person.getActivity().last().to();

        System.out.println(person.getName() + " is currently in " + from.getName() + " with ID " + from.getId());
        System.out.println(getAccessibleRooms(from.getId(), person));

        System.out.println("Enter room ID: ");
        id = Tools.getInt();
        while (getRoomById(id) == null) {
            System.out.println("Room not found.");
            System.out.println("Enter room ID: ");
            id = Tools.getInt();
        }
        Room to = getRoomById(id);
        if (getAccessibleRooms(from.getId(), person).contains(to) && hasPermission(person, to)) {
            return new Event(person, from, to, LocalDateTime.now());
        } else {
            while (!getAccessibleRooms(from.getId(), person).contains(to)) {
                System.out.println("Sorry, room not accessible");

                System.out.println(getAccessibleRooms(from.getId(), person));
                System.out.println("\nEnter Room ID:");
                to = getRoomById(Tools.getInt());
            }
            return null;
        }
    }

    /**
     * Find the closest exit given a room user chose.
     *
     * @param startRoom The room from which to find the closest exit.
     */
    public void findClosestExit(int startRoom) {
        UnorderedListADT<Room> exits = getExits();

        Iterator<Room> exitIterator = exits.iterator();
        while (exitIterator.hasNext()) {
            int exitRoom = exitIterator.next().getId();
            double weight = hospitalMap.shortestPathWeight(getRoomById(startRoom), getRoomById(exitRoom));

            Iterator<Room> path = findShortestPath(startRoom, exitRoom);
            System.out.println("\n---------------------------------------------------------");
            System.out.println("\nPath from room " + startRoom + " to exit " + exitRoom);
            System.out.println("Weight: " + weight);
            System.out.println("\nMove to");
            while (path.hasNext()) {
                Room room2 = path.next();
                System.out.println(room2.getName());
            }
            System.out.println("\n----------------------------------------------------------");
        }
    }

    /**
     * Get all exits in the hospital.
     *
     * @return A list of all exits in the hospital.
     */
    private UnorderedListADT<Room> getExits() {

        UnorderedListADT<Room> exits = new UnorderedLinkedList<>();
        UnorderedListADT<Edge> edges = getEdges();
        Iterator<Edge> edgeIterator = edges.iterator();

        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            Room room1 = edge.getRoom1();
            Room room2 = edge.getRoom2();

            if (room1.getType().equals(TypeOfRoom.EXIT)) {
                if (exits.isEmpty()) {
                    exits.addToRear(room1);
                } else if (room1.getType().equals(TypeOfRoom.EXIT) && !(exits.contains(room1))) {
                    exits.addToRear(room1);
                }
            }
            if (room2.getType().equals(TypeOfRoom.EXIT)) {
                if (exits.isEmpty()) {
                    exits.addToRear(room2);
                } else if (room2.getType().equals(TypeOfRoom.EXIT) && !(exits.contains(room2))) {
                    exits.addToFront(room2);
                }
            }
        }
        return exits;
    }

    /**
     * Get all edges of the hospital network.
     *
     * @return A list of all edges in the hospital network.
     */
    private UnorderedListADT<Edge> getEdges() {

        UnorderedListADT<Edge> edges = new UnorderedLinkedList<>();

        Room room1;
        Room room2;
        int weight;

        int size = hospitalMap.size();
        for (int i = 0; i < size; i++) {
            room1 = hospitalMap.getVertex(i);
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    continue;
                }
                room2 = hospitalMap.getVertex(j);
                if (hospitalMap.edgeExists(room1, room2)) {
                    weight = (int) hospitalMap.getWeight(room1, room2);
                    edges.addToRear(new Edge(room1, room2, weight));
                }
            }
        }
        if (edges.isEmpty()) {
            System.out.println("No edges found");
        }
        return edges;
    }

    /**
     * Detects contacts of a person within a specified date range across all rooms.
     *
     * @param personId The ID of the person to check contacts for.
     * @param from     The start of the date range.
     * @param to       The end of the date range.
     * @return A list of people who had contact with the individual within the specified time frame.
     */
    public UnorderedListADT<Person> hadContactWithIndividual(int personId, LocalDateTime from, LocalDateTime to) {
        UnorderedListADT<Person> contacts = new UnorderedLinkedList<>();
        Iterator<Room> roomIterator = hospitalMap.iteratorBFS(0);

        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            UnorderedListADT<Person> peopleInRoom = room.hadContactWithIndividual(personId, from, to);
            Iterator<Person> personIterator = peopleInRoom.iterator();
            int count = 0;
            while (personIterator.hasNext()) {
                Person person = personIterator.next();
                if (count == 0) {
                    contacts.addToRear(person);
                } else if (!contacts.contains(person))
                    contacts.addToRear(person);
                count++;
            }
            System.out.println(count);

        }

        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }

    /**
     * Detects all people who had contact with a room within a specified date range.
     *
     * @param roomId The ID of the room to check contacts for.
     * @param from   The start of the date range.
     * @param to     The end of the date range.
     * @return A list of people who were in the room within the specified time frame.
     */
    public UnorderedListADT<Person> hadContactWithRoom(int roomId, LocalDateTime from, LocalDateTime to) {
        Room room = getRoomById(roomId);
        UnorderedListADT<Person> contacts = room.hadContactWithRoom(from, to);
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }

    /**
     * Get all rooms in the hospital.
     *
     * @return A list of all rooms in the hospital.
     */
    public UnorderedListADT<Room> getAllRooms() {
        UnorderedListADT<Room> allRooms = new UnorderedLinkedList<>();
        Iterator<Room> roomIterator = hospitalMap.iteratorDFS(0);

        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            allRooms.addToRear(room);
        }

        return allRooms;
    }

    /**
     * Get all people in the hospital.
     *
     * @return A list of all people in the hospital.
     */
    public UnorderedListADT<Person> getAllPeople() {
        UnorderedListADT<Person> allPeople = new UnorderedLinkedList<>();
        UnorderedListADT<Room> allRooms = getAllRooms();

        Iterator<Room> roomIterator = allRooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            UnorderedListADT<Person> peopleInRoom = room.getPeopleInRoom();
            Iterator<Person> personIterator = peopleInRoom.iterator();
            int count = 0;
            while (personIterator.hasNext()) {
                Person person = personIterator.next();
                if (count == 0) {
                    allPeople.addToRear(person);
                } else if (!allPeople.contains(person))
                    allPeople.addToRear(person);
                count++;
            }
        }

        return allPeople;
    }

    /**
     * Get all events in the hospital.
     * This method iterates over all rooms in the hospital and retrieves the events that occurred in each room.
     * It then adds the events to a list of all events in the hospital.
     *
     * @return A list of all events in the hospital.
     */
    public UnorderedListADT<Event> getAllEvents() {
        UnorderedListADT<Event> allEvents = new UnorderedLinkedList<>();
        UnorderedListADT<Room> allRooms = getAllRooms();

        Iterator<Room> roomIterator = allRooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            UnorderedListADT<Event> eventsInRoom = room.getEvents();
            Iterator<Event> eventIterator = eventsInRoom.iterator();
            while (eventIterator.hasNext()) {
                Event event = eventIterator.next();
                allEvents.addToRear(event);
            }
        }
        return allEvents;
    }

    /**
     * Finds the shortest path from a start vertex to an end vertex, avoiding occupied locations.
     * This method utilizes the network's shortest path algorithm, considering the locations to avoid.
     *
     * @param startVertex The starting vertex in the network (bot's location).
     * @param endVertex   The end vertex for the path (enemy's flag location).
     * @return An iterator over the sequence of vertices in the shortest path.
     */
    private Iterator<Room> findShortestPath(int startVertex, int endVertex) {
        Room from = getRoomById(startVertex);
        Room to = getRoomById(endVertex);
        return hospitalMap.findShortestPath(from, to);
    }

    /**
     * Get all rooms that are accessible from the current room.
     *
     * @param person        The person trying to access the rooms.
     * @param currentRoomId The ID of the current room.
     * @return A list of rooms that are accessible from the current room.
     */
    public UnorderedListADT<Room> getAccessibleRooms(int currentRoomId, Person person) {
        UnorderedListADT<Room> accessibleRooms = new UnorderedLinkedList<>();
        UnorderedListADT<Edge> edges = getEdges();
        Iterator<Edge> edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            if (edge.getRoom1() == getRoomById(currentRoomId) && hasPermission(person, edge.getRoom2())) {
                if (accessibleRooms.isEmpty()) {
                    accessibleRooms.addToRear(edge.getRoom2());
                } else if (!accessibleRooms.contains(edge.getRoom2())) {
                    accessibleRooms.addToRear(edge.getRoom2());
                }
            }
            if (edge.getRoom2() == getRoomById(currentRoomId) && hasPermission(person, edge.getRoom1())) {
                if (accessibleRooms.isEmpty()) {
                    accessibleRooms.addToRear(edge.getRoom1());
                } else if (!accessibleRooms.contains(edge.getRoom1())) {
                    accessibleRooms.addToRear(edge.getRoom1());
                }
            }
        }
        return accessibleRooms;
    }

    /*public static Room readRoom() throws IOException {

        UnorderedListADT<TypeOfFunction> access = new UnorderedLinkedList<>();

        System.out.println("Choose a name for the room: ");
        String name = Tools.getString();

        System.out.println("Choose a maximum room capacity: ");
        int capacity = Tools.getInt();

        System.out.println("Choose a room type: ");
        TypeOfRoom type = chooseTypeOfRoom();
        System.out.println("Room created: " + name + " " + type + " " + capacity);

        //location goes here, not implemented yet, get all available locations

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Choose a function to grant access to this room");
            TypeOfFunction function = manageAccess();
            System.out.println("Access granted to: " + function);
            access.addToRear(function);
            System.out.println("Do you want to grant access to another function? (Y/N)");
            String choice = Tools.getString();
            if (choice.equals("N") || choice.equals("n")) {
                isRunning = false;
            }
        }
        return new Room(name, type, capacity, access);
    }
*/
}
