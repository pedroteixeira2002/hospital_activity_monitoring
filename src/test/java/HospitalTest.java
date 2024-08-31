import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.Event;
import hospital.Hospital;
import hospital.Person;
import hospital.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HospitalTest {
    private Hospital hospital;

    @BeforeEach
    void setUp() {
        // Initialize a new Hospital object before each test
        hospital = new Hospital();

    }

    @Test
    void testAddRoom() {
        Room room = mock(Room.class);
        Room addedRoom = hospital.addRoom(room);
        assertNotNull(addedRoom, "The room should be added successfully");
        assertEquals(room, addedRoom, "The returned room should match the added room");
        assertTrue(hospital.getRooms().contains(room), "The room should be in the hospital's room list");

        // Test adding a null room
        Room nullRoom = hospital.addRoom(null);
        assertNull(nullRoom, "Adding a null room should return null");
    }

    @Test
    void testAddPerson() {
        Person person = mock(Person.class);
        Person addedPerson = hospital.addPerson(person);
        assertNotNull(addedPerson, "The person should be added successfully");
        assertEquals(person, addedPerson, "The returned person should match the added person");
        assertTrue(hospital.getPeople().contains(person), "The person should be in the hospital's people list");

        // Test adding a null person
        Person nullPerson = hospital.addPerson(null);
        assertNull(nullPerson, "Adding a null person should return null");
    }

    @Test
    void testAddEvent() {
        Person person = mock(Person.class);
        Room roomFrom = mock(Room.class);
        Room roomTo = mock(Room.class);

        when(person.getId()).thenReturn(1);
        when(person.getName()).thenReturn("John Doe");
        when(roomTo.isOccupied()).thenReturn(false);
        when(roomTo.getCapacity()).thenReturn(1);
        when(roomTo.getCurrentOccupation()).thenReturn(0);

        Event event = new Event(person, roomFrom, roomTo, LocalDateTime.now());

        hospital.addEvent(event);

        assertEquals(1, hospital.getEvents().size(), "The event should be added to the queue");
    }

    @Test
    void testGetPersonById() {
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1);
        hospital.addPerson(person);

        Person retrievedPerson = hospital.getPersonById(1);
        assertNotNull(retrievedPerson, "The person should be found");
        assertEquals(person, retrievedPerson, "The retrieved person should match the added person");

        // Test retrieving a person that doesn't exist
        Person nonExistentPerson = hospital.getPersonById(2);
        assertNull(nonExistentPerson, "Retrieving a non-existent person should return null");
    }

    @Test
    void testGetRoomById() {
        Room room = mock(Room.class);
        when(room.getId()).thenReturn(1);
        hospital.addRoom(room);

        Room retrievedRoom = hospital.getRoomById(1);
        assertNotNull(retrievedRoom, "The room should be found");
        assertEquals(room, retrievedRoom, "The retrieved room should match the added room");

        // Test retrieving a room that doesn't exist
        Room nonExistentRoom = hospital.getRoomById(2);
        assertNull(nonExistentRoom, "Retrieving a non-existent room should return null");
    }

    @Test
    void testHadContactWithIndividual() {
        // Create mocks for Person and Room
        Person person1 = mock(Person.class);
        Person person2 = mock(Person.class);
        Room room = mock(Room.class);
        Room room2 = mock(Room.class);

        // Mock the behavior of the Person and Room objects
        when(person1.getId()).thenReturn(1);
        when(person1.getLocation()).thenReturn(room);
        when(person2.getLocation()).thenReturn(room);

        // Add the room to the hospital (if required by your Hospital class logic)
        hospital.addRoom(room);
        hospital.addRoom(room2);
        // Add the persons to the hospital
        hospital.addPerson(person1);
        hospital.addPerson(person2);

        // Create events for each person in the same room at the same time
        Event event1 = new Event(person1, room, room2, LocalDateTime.now().minusHours(3));
        Event event2 = new Event(person2, room, room2, LocalDateTime.now().minusHours(3));

        // Add events to the hospital
        hospital.addEvent(event1);
        hospital.addEvent(event2);

        // Assertions to check if the contact detection is working correctly
        assertFalse(hospital.hadContactWithIndividual(person1.getId(),
                LocalDateTime.now().minusDays(2), LocalDateTime.now()).isEmpty(),
                "Contacts should be detected");
        assertTrue(hospital.hadContactWithIndividual(
                person1.getId(), LocalDateTime.now().minusDays(2), LocalDateTime.now()).contains(person2),
                "Person 2 should be detected as a contact of person 1");
    }


    @Test
    void testHadContactWithRoom() {
        Person person1 = mock(Person.class);
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);

        when(person1.getId()).thenReturn(1);
        when(room1.getId()).thenReturn(1);

        hospital.addRoom(room1);
        hospital.addRoom(room2);

        hospital.addPerson(person1);
        hospital.addRoom(room1);

        Event event1 = new Event(person1, room1, room2, LocalDateTime.now().minusHours(1));
        hospital.addEvent(event1);

        UnorderedListADT<Person> contacts = hospital.hadContactWithRoom(1, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        assertFalse(contacts.isEmpty(), "Contacts with the room should be detected");
        assertTrue(contacts.contains(person1), "Person 1 should be detected as having contact with room 1");
    }
}
