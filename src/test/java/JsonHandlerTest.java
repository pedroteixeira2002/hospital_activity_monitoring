import collections.graphs.Network;
import collections.lists.UnorderedLinkedList;
import collections.queues.LinkedQueue;
import collections.queues.QueueADT;
import hospital.*;
import hospital.enums.TypeOfFunction;
import hospital.exceptions.ImportException;
import hospital.io.JsonHandler;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonHandlerTest {

    private Hospital hospital;

    @BeforeEach
    void setUp() {
        hospital = mock(Hospital.class);
    }

    @Test
    void testImportRooms() throws IOException {
        // Create a temporary JSON file for rooms
        File tempFile = File.createTempFile("rooms", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[{\"id\": 1, \"name\": \"Room A\", \"type\": \"EXIT\", \"capacity\": 2, \"currentOccupation\": 1, \"occupied\": true, \"access\": [\"DOCTOR\", \"NURSE\"]}]");
        }

        // Run importRooms
        JsonHandler.importRooms(hospital, tempFile.getAbsolutePath());

        // Verify if the room was added to the hospital
        verify(hospital, times(1)).addRoom(any(Room.class));
    }

    @Test
    void testExportPeople() throws IOException {
        // Mock the person and its behavior
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1);
        when(person.getName()).thenReturn("John Doe");
        when(person.getAge()).thenReturn(30);
        when(person.getFunction()).thenReturn(TypeOfFunction.DOCTOR);
        when(person.getLocation()).thenReturn(mock(Room.class));

        // Mock hospital to return the person
        when(hospital.getPeople()).thenReturn(new UnorderedLinkedList<Person>() {{
            addToRear(person);
        }});

        // Create a temporary JSON file for people
        File tempFile = File.createTempFile("people", ".json");
        tempFile.deleteOnExit();

        // Run exportPeople
        JsonHandler.exportPeople(hospital, tempFile.getAbsolutePath());

        // Read the output file and verify its contents
        try (FileReader reader = new FileReader(tempFile)) {
            char[] buffer = new char[(int) tempFile.length()];
            reader.read(buffer);
            String fileContent = new String(buffer);
            assertTrue(fileContent.contains("\"id\":1"));
            assertTrue(fileContent.contains("\"name\":\"John Doe\""));
        }
    }

    @Test
    void testImportPeople() throws IOException {
        // Create a temporary JSON file for people
        File tempFile = File.createTempFile("people", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[{\"id\": 1, \"name\": \"John Doe\", \"age\": 30, \"function\": \"DOCTOR\", \"location\": 1}]");
        }

        // Mock behavior for getRoomById
        Room room = mock(Room.class);
        when(hospital.getRoomById(1)).thenReturn(room);

        // Run importPeople
        JsonHandler.importPeople(hospital, tempFile.getAbsolutePath());

        // Verify if the person was added to the hospital
        verify(hospital, times(1)).addPerson(any(Person.class));
    }

    @Test
    void testImportEvents() throws IOException {
        // Create a temporary JSON file for events
        File tempFile = File.createTempFile("events", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[{\"personId\": 1, \"fromRoomId\": 1, \"toRoomId\": 2, \"time\": \"2024-08-29T10:00:00\"}]");
        }

        // Mock behavior for getPersonById and getRoomById
        Person person = mock(Person.class);
        Room fromRoom = mock(Room.class);
        Room toRoom = mock(Room.class);
        when(hospital.getPersonById(1)).thenReturn(person);
        when(hospital.getRoomById(1)).thenReturn(fromRoom);
        when(hospital.getRoomById(2)).thenReturn(toRoom);

        // Run importEvents
        JsonHandler.importEvents(hospital, tempFile.getAbsolutePath());

        // Verify if the event was added to the hospital
        verify(hospital, times(1)).addEvent(any(Event.class));
    }

    @Test
    void testExportEvents() throws IOException {
        // Mock the event and its behavior
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1);

        Room fromRoom = mock(Room.class);
        when(fromRoom.getId()).thenReturn(1);

        Room toRoom = mock(Room.class);
        when(toRoom.getId()).thenReturn(2);

        Event event = mock(Event.class);
        when(event.getPerson()).thenReturn(person);
        when(event.getFrom()).thenReturn(fromRoom);
        when(event.getTo()).thenReturn(toRoom);
        when(event.getTime()).thenReturn(LocalDateTime.parse("2024-08-29T10:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // Mock hospital to return the event
        QueueADT<Event> events = new LinkedQueue<>();
        events.enqueue(event);
        when(hospital.getEvents()).thenReturn(events);

        // Create a temporary JSON file for events
        File tempFile = File.createTempFile("events", ".json");
        tempFile.deleteOnExit();

        // Run exportEvents
        JsonHandler.exportEvents(hospital, tempFile.getAbsolutePath());

        // Read the output file and verify its contents
        try (FileReader reader = new FileReader(tempFile)) {
            char[] buffer = new char[(int) tempFile.length()];
            reader.read(buffer);
            String fileContent = new String(buffer);
            assertTrue(fileContent.contains("\"personId\":1"));
            assertTrue(fileContent.contains("\"fromRoomId\":1"));
            assertTrue(fileContent.contains("\"toRoomId\":2"));
        }
    }

    @Test
    void testImportMap() throws IOException, ImportException {
        Hospital hospital = new Hospital();
        // Create a temporary JSON file for map data
        File tempFile = File.createTempFile("map", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[{\"room1\": 1, \"room2\": 2, \"weight\": 10}]");
        }

        // Run importMap
        Network<Integer> network = JsonHandler.importMap(hospital, tempFile.getAbsolutePath());

        // Verify if the network was created correctly
        assertNotNull(network);
        assertTrue(network.containsVertex(1));
        assertTrue(network.containsVertex(2));
        assertTrue(network.edgeExists(1, 2));
    }
}
