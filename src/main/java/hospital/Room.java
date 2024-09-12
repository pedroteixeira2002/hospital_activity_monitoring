package hospital;

import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.enums.TypeOfFunction;
import hospital.enums.TypeOfRoom;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

import static hospital.menu.ReadInfo.manageAccess;

/**
 * The Room class represents a room in the hospital.
 * It contains the room's id, name, type, capacity, current occupation, occupied status and access.
 * The access is a list of functions that have access to the room.
 * The room can be occupied or not.
 * The room can be accessed by different functions.
 * The room can be revoked access to a function.
 * The room can be printed.
 * The room can be incremented or decremented in occupation.
 */
public class Room {
    /**
     * The room id
     */
    private int id;
    /**
     * The room name
     */
    private final String name;
    /**
     * The room type
     */
    private final TypeOfRoom type;
    /**
     * The room capacity
     */
    private final int capacity;
    /**
     * The room current occupation
     */
    private int currentOccupation;
    /**
     * The room occupied status
     **/
    private boolean occupied;
    /**
     * The list of people who have access to the room
     */
    private UnorderedListADT<TypeOfFunction> access;
    /**
     * The list of events that take place in the room
     */
    private UnorderedListADT<Event> events;
    /**
     * The people in the room
     */
    private UnorderedListADT<Person> peopleInRoom;

    /**
     * Constructor of the class Room
     *
     * @param id                the room id
     * @param access            the list of people who have access to the room
     * @param currentOccupation the current occupation of the room
     * @param occupied          the room occupied status
     * @param capacity          the room capacity
     * @param type              the room type
     * @param name              the room name
     **/
    public Room(int id, UnorderedListADT<TypeOfFunction> access, int currentOccupation,
                boolean occupied, int capacity, TypeOfRoom type, String name) {
        this.id = id;
        this.access = access;
        this.currentOccupation = currentOccupation;
        this.occupied = occupied;
        this.capacity = capacity;
        this.type = type;
        this.name = name;
        this.events = new UnorderedLinkedList<>();
        this.peopleInRoom = new UnorderedLinkedList<>();
    }

    /**
     * Constructor of the class Room
     **/
    public Room() {
        this.id = 0;
        this.name = "";
        this.type = null;
        this.capacity = 0;
        this.currentOccupation = 0;
        this.occupied = false;
        this.access = new UnorderedLinkedList<>();
        this.peopleInRoom = new UnorderedLinkedList<>();
    }

    /**
     * Get the room id
     *
     * @return the room id
     */
    public final int getId() {
        return id;
    }

    /**
     * Set the room id
     *
     * @param id the room id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the room name
     *
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the room type
     *
     * @return the room type
     */
    public TypeOfRoom getType() {
        return type;
    }

    /**
     * Get the people in the room
     *
     * @return the people in the room
     */
    public UnorderedListADT<Person> getPeopleInRoom() {
        return peopleInRoom;
    }

    /**
     * Get the room capacity
     *
     * @return the room capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the room current occupation
     *
     * @return the room current occupation
     */
    public int getCurrentOccupation() {
        return currentOccupation;
    }

    /**
     * Increment the room occupation
     */
    public void increaseOccupation() {
        this.currentOccupation++;
    }

    /**
     * Decrement the room occupation
     */
    public void decreaseOccupation() {
        this.currentOccupation--;
    }

    /**
     * Get the room occupied status
     *
     * @return true if the room is occupied, false otherwise
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Set the room occupied status
     *
     * @param occupied the room occupied status
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * List of the events that take place in the room
     *
     * @return the event list
     */
    public UnorderedListADT<Event> getEvents() {
        return events;
    }

    /**
     * Add an event to the room
     *
     * @param event the event to add
     *
     * @return true if the event was added, false otherwise
     */
    public boolean addEvent(Event event) {
    this.events.addToRear(event);
    this.peopleInRoom.addToRear(event.getPerson());
    return true;
    }

    /**
     * Add a type of function to access the room
     *
     * @param function the type of function
     */
    public void addAccess(TypeOfFunction function) {
        this.access.addToRear(function);
    }

    /**
     * Add a person to the room
     *
     * @param person the person to add
     */
    public void addPerson(Person person) {
        this.peopleInRoom.addToRear(person);
    }

    /**
     * Remove a person from the room
     *
     * @param person the person to remove
     */
    public void removePerson(Person person) {
        if (peopleInRoom.isEmpty()) {
            System.out.println("No personnel currently in " + getName());
            return;
        }
        this.peopleInRoom.remove(person);
    }

    /**
     * Remove a type of function from the room access
     *
     * @param function the type of function
     */
    public void removeAccess(TypeOfFunction function) {
        this.access.remove(function);
    }

    /**
     * Get the list of functions who have access to the room
     *
     * @return the room access list
     */
    public UnorderedListADT<TypeOfFunction> getAccess() {
        return access;
    }

    /**
     * Revoke a function access to the room
     *
     * @throws IOException if an I/O error occurs
     */
    public void revokeAccess() throws IOException {
        System.out.println("Choose a function to revoke access to this room");

        UnorderedListADT<TypeOfFunction> roomAccess = getAccess();
        if (roomAccess == null || roomAccess.isEmpty()) {
            System.out.println("No personnel currently have access to " + getName());
            return;
        }
        System.out.println(roomAccess);
        TypeOfFunction function = manageAccess();

        removeAccess(function);
        System.out.println("Access revoked from: " + function);

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

        UnorderedListADT<Person> contacts = new UnorderedLinkedList<>();
        Iterator<Event> eventsIterator = this.events.iterator();
        while (eventsIterator.hasNext()) {
            Event event = eventsIterator.next();
            if (event.getPerson().getId() != id && event.getTime().isAfter(from) && event.getTime().isBefore(to))
                contacts.addToRear(event.getPerson());
        }
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }

    /**
     * Detects all people who had contact with a room within a specified date range.
     *
     * @param from The start of the date range.
     * @param to   The end of the date range.
     * @return A list of people who were in the room within the specified time frame.
     */
    public UnorderedListADT<Person> hadContactWithRoom(LocalDateTime from, LocalDateTime to) {
        UnorderedListADT<Person> contacts = new UnorderedLinkedList<>();
        Iterator<Event> eventsIterator = this.events.iterator();
        while (eventsIterator.hasNext()) {
            Event event = eventsIterator.next();
            if (event.getTime().isAfter(from) && event.getTime().isBefore(to)) {
                contacts.addToRear(event.getPerson());
            }
        }
        if (contacts.isEmpty()) {
            System.out.println("No contacts found");
        }
        return contacts;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return id == room.id;
    }
    @Override
    public String toString() {
        return "\n---------------------------------------------" +
                "\nRoom\tID :\t" + id +
                "\nName :\t" + name +
                "\nType :\t" + type +
                "\nCapacity: \t" + capacity +
                "\nCurrent Occupation :\t" + currentOccupation +
                "\nIs full ? :\t" + occupied +
                "\nAccess :\t" + getAccess() +
                "\n----------------------------------------------\n";
    }
}
