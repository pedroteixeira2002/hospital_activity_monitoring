package hospital;


import java.time.LocalDateTime;

/**
 * This class will be used to store the events that happen in the hospital
 * It will store the person that is involved in the event, the room from where the person is moving,
 * the room to where the person is moving and the time of the event
 *
 * @param person The person that is involved in the event
 * @param from   The room from where the person is moving
 * @param to     The room to where the person is moving
 * @param time   The time of the event
 */
public record Event(Person person, Room from, Room to, LocalDateTime time) {

    /**
     * Get the person that is involved in the event
     *
     * @return The person that is involved in the event
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Get the room from where the person is moving
     *
     * @return The room from where the person is moving
     */
    public Room getFrom() {
        return from;
    }

    /**
     * Get the room to where the person is moving
     *
     * @return The room to where the person is moving
     */
    public Room getTo() {
        return to;
    }

    /**
     * Get the time of the event
     *
     * @return The time of the event
     */
    public LocalDateTime getTime() {
        return time;
    }


    @Override
    public String toString() {
        return "\nEvent\n" + person.toString() +
                "\nFrom Room : \t" + from.toString() +
                "\nTo Room : \t" + to.toString() +
                "\nTime : \t" + time;
    }
}
