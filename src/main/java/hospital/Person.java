package hospital;

import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.enums.TypeOfFunction;

/**
 * The Person class represents a person in the hospital.
 * It contains the person's id, name, age, function and location.
 * The location is represented by a Room object.
 * The function is represented by a TypeOfFunction enum.
 */
public class Person {
    /**
     * The person id
      */
    private final int id;
    /**
     * The person name
      */
    private final String name;
    /**
     *  The person age
      */
    private final int age;
    /**
     * The person function
      */
    private final TypeOfFunction function;
    /**
     * The person location
      */
    private Room location;

    /**
     * The person activity in the hospital
     */
    private UnorderedListADT activity;
    /**
     * Constructor of the class
     *
     * @param id the person id
     * @param name the person name
     * @param age the person age
     * @param function the person function in the hospital
     * @param location the person location in the hospital (room)
     **/
    public Person(int id, String name, int age, TypeOfFunction function, Room location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.function = function;
        this.location = location;
        this.activity = new UnorderedLinkedList();
    }

    /**
     * Get the person id
     *
     * @return the person id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the person location
     *
     * @return the person location
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Set the person location
     *
     * @param location the person location
     */
    public void setLocation(Room location) {
        this.location = location;
    }

    /**
     * Get the person name
     *
     * @return the person name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the person activity
     *
     * @return the person activity
     */
    public UnorderedListADT getActivity() {
        return activity;
    }

    /**
     * Get the person age
     *
     * @return the person age
     */
    public int getAge() {
        return age;
    }

    /**
     * Get the person function
     *
     * @return the person function
     */
    public TypeOfFunction getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return "\n---------------------------------------------" +
                "\nPerson ID :\t" + id +
                "\nName : \t" + name +
                "\nAge :\t" + age +
                "\nFunction :\t" + function +
                "\nLocation :\t" + location +
                "\n----------------------------------------------\n";

    }
}

