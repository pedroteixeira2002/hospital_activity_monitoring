package hospital;

import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.enums.TypeOfFunction;
import hospital.enums.TypeOfRoom;


import java.io.IOException;

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
    private final int id;
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

   /* public Room(String name, TypeOfRoom type, int capacity, UnorderedListADT<TypeOfFunction> access) {
        this.id = idCounter++;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.currentOccupation = 0;
        this.occupied = false;
        this.access = access;
    }*/


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
     * Add a type of function to access the room
     *
     * @param function the type of function
     */
    public void addAccess(TypeOfFunction function) {
        this.access.addToRear(function);
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
