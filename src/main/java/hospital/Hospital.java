package hospital;

import collections.graphs.Network;
import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import collections.queues.LinkedQueue;
import collections.queues.QueueADT;
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
     * The rooms in the hospital.
     */
    private final UnorderedListADT<Room> rooms;
    /**
     * The people in the hospital.
     */
    private final UnorderedListADT<Person> people;
    /**
     * The events in the hospital.
     */
    private final QueueADT<Event> events;

    /**
     * Default constructor that initializes the hospital with an empty map, rooms, people, and events.
     * It also initializes the list of edges.
     */
    public Hospital() {
        this.hospitalMap = new Network();
        this.rooms = new UnorderedLinkedList<>();
        this.people = new UnorderedLinkedList<>();
        this.events = new LinkedQueue<>();
    }

    /**
     * Sets the hospital map.
     *
     * @param hospitalMap The hospital map to set.
     */
    public void setHospitalMap(Network hospitalMap) {
        this.hospitalMap = hospitalMap;
    }

    /**
     * Adds a room to the hospital map. If the room is null, it will not be added.
     *
     * @param room The room to add.
     * @return The room added to the hospital map.
     */
    public Room addRoom(Room room) {
        if (room == null) {
            System.out.println("Room not created");
            return null;
        }
        this.rooms.addToRear(room);
        System.out.println("Room added successfully");
        return room;
    }

    /**
     * Adds a person to the hospital map. If the person is null, it will not be added.
     *
     * @param person The person to add.
     * @return The person added to the hospital map.
     */
    public Person addPerson(Person person) {
        if (person == null) {
            System.out.println("Person not created");
            return null;
        }
        this.people.addToRear(person);
        System.out.println("Person added successfully");
        return person;
    }

    /**
     * Adds an event to the hospital map. If the event is null, it will not be added.
     *
     * @param event The event to add.
     */
    public void addEvent(Event event) {
        if (event == null) {
            System.out.println("Event not created");
            return;
        }
        if (event.getFrom().equals(event.getTo())) {
            System.out.println("The person is already in the room");
            return;
        }
        if (!event.getTo().isOccupied()) {
            this.events.enqueue(event);
            event.getTo().increaseOccupation();
            event.getPerson().setLocation(event.getTo());
            if (event.getFrom().getCurrentOccupation() > 0)
                event.getFrom().decreaseOccupation();
            if (event.getTo().getCapacity() == event.getTo().getCurrentOccupation()) {
                event.getTo().setOccupied(true);
                System.out.println("The room " + event.getTo().getName() + " is now occupied.");
            }
            if (event.getFrom().getCapacity() > event.getFrom().getCurrentOccupation()) {
                event.getFrom().setOccupied(false);
            }
            System.out.println(event.getPerson().getName() + ", with the identification number " +
                    event.getPerson().getId() + " has moved from " + event.getFrom().getName() +
                    " to " + event.getTo().getName() + " at " + event.getTime());
        } else {
            System.out.println("The room is already occupied" +
                    "\nPlease choose another room or try again later");

        }
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

        //add nodes
        for (int i = 0; i < rooms.size(); i++) {
            graph.addNode(Integer.toString(i));
            Node node = graph.getNode(Integer.toString(i));
            node.addAttribute("ui.label", Integer.toString(i));
        }

        // add edges and respective weights
        for (int i = 0; i < rooms.size(); i++) {
            Room room1 = getRoomById(i);
            for (int j = 0; j < rooms.size(); j++) {
                Room room2 = getRoomById(j);
                if (hospitalMap.edgeExists(room1, room2) && graph.getEdge(i + "_" + j) == null) {
                    double weight = hospitalMap.getWeight(room1, room2);
                    String edgeId = i + "_" + j;
                    graph.addEdge(edgeId, Integer.toString(i), Integer.toString(j), true); // true para arestas direcionadas se necessário
                    graph.getEdge(edgeId).addAttribute("ui.label", String.format("%.2f", weight));
                }
            }
        }

        graph.display();
    }

    /**
     * Get all rooms in the hospital.
     *
     * @return A list of all rooms in the hospital.
     */
    public UnorderedListADT<Room> getRooms() {
        return rooms;
    }

    /**
     * Get all people in the hospital.
     *
     * @return A list of all people in the hospital.
     */
    public UnorderedListADT<Person> getPeople() {
        return people;
    }

    /**
     * Get all events in the hospital.
     *
     * @return A list of all events in the hospital.
     */
    public QueueADT<Event> getEvents() {
        return events;
    }

    /**
     * Get someone by their ID.
     *
     * @param id The ID of the person to get.
     * @return The person with the specified ID.
     */
    public Person getPersonById(int id) {
        Iterator<Person> peopleIterator = people.iterator();
        while (peopleIterator.hasNext()) {
            Person person = peopleIterator.next();
            if (person.getId() == id) {
                return person;
            }
        }
        System.out.println("\nPerson not recognized, please contact support");
        return null;
    }

    /**
     * Get a room by its ID.
     *
     * @param id The ID of the room to get.
     * @return The room with the specified ID.
     */
    public Room getRoomById(int id) {
        Iterator<Room> roomsIterator = rooms.iterator();
        while (roomsIterator.hasNext()) {
            Room room = roomsIterator.next();
            if (room.getId() == id) {
                return room;
            }
        }
        System.out.println("\nRoom not recognized, please contact support");
        return null;
    }

    /**
     * Detects contacts of a person within a specified date range.
     *
     * @param id   The ID of the person to check contacts for.
     * @param from The start of the date range.
     * @param to   The end of the date range.
     * @return A list of IDs of people who were in the same room within the specified time frame.
     */
    public UnorderedListADT<Person> hadContactWithIndividual(int id, LocalDateTime from, LocalDateTime to) {
        Person person = getPersonById(id);
        UnorderedListADT<Person> contacts = new UnorderedLinkedList<>();

        for (int i = 0; i < events.size(); i++) {
            Event event = events.dequeue();
            //if the event is within the specified date range
            if (event.getTime().isAfter(from) && event.getTime().isBefore(to)) {
                //if the person is in the same room as the person we are looking for and is not the same person
                if (event.getTo().equals(person.getLocation()) && !event.getPerson().equals(person)) {
                    //add the person to the list of contacts
                    contacts.addToRear(event.getPerson());
                }
                events.enqueue(event);
            }
        }
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }

    /**
     * Detects all people who had contact with a room within a specified date range.
     *
     * @param id   The ID of the room to check contacts for.
     * @param from The start of the date range.
     * @param to   The end of the date range.
     * @return A list of people who were in the room within the specified time frame.
     */
    public UnorderedListADT<Person> hadContactWithRoom(int id, LocalDateTime from, LocalDateTime to) {
        Room room = getRoomById(id);
        UnorderedListADT<Person> contacts = new UnorderedLinkedList<>();

        for (int i = 0; i < events.size(); i++) {
            Event event = events.dequeue();
            //if the event is within the specified date range and happened in the room we are looking for
            if (event.getTime().isAfter(from) && event.getTime().isBefore(to) && event.getTo().equals(room)) {
                //add the person to the list of contacts
                contacts.addToRear(event.getPerson());
            }
            events.enqueue(event);
        }
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }

    /**
     * Identifies if someone who is trying to enter a room has permission to do so.
     *
     * @param person
     * @param room
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

        Room from = person.getLocation();

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
                if (exits.isEmpty() == true) {
                    exits.addToRear(room1);
                } else if (room1.getType().equals(TypeOfRoom.EXIT) && !(exits.contains(room1))) {
                    exits.addToRear(room1);
                }
            }
            if (room2.getType().equals(TypeOfRoom.EXIT)) {
                if (exits.isEmpty() == true) {
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
     * @param person The person trying to access the rooms.
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
