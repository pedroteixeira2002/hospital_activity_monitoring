
import collections.lists.UnorderedLinkedList;
import collections.lists.UnorderedListADT;
import hospital.Room;
import hospital.enums.TypeOfFunction;
import hospital.enums.TypeOfRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class RoomTest {

    private Room room;
    private UnorderedListADT<TypeOfFunction> mockAccessList;

    @BeforeEach
    void setUp() {
        mockAccessList = new UnorderedLinkedList<>();
        room = new Room(1, mockAccessList, 0, false, 2, TypeOfRoom.EXIT, "ICU Room A");
    }

    @Test
    void testDefaultConstructor() {
        Room defaultRoom = new Room();
        assertEquals(0, defaultRoom.getId());
        assertEquals("", defaultRoom.getName());
        assertNull(defaultRoom.getType());
        assertEquals(0, defaultRoom.getCapacity());
        assertEquals(0, defaultRoom.getCurrentOccupation());
        assertFalse(defaultRoom.isOccupied());
        assertNotNull(defaultRoom.getAccess());
        assertTrue(defaultRoom.getAccess().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals(1, room.getId());
        assertEquals("ICU Room A", room.getName());
        assertEquals(TypeOfRoom.EXIT, room.getType());
        assertEquals(2, room.getCapacity());
        assertEquals(0, room.getCurrentOccupation());
        assertFalse(room.isOccupied());
        assertEquals(mockAccessList, room.getAccess());
    }

    @Test
    void testIncreaseOccupation() {
        room.increaseOccupation();
        assertEquals(1, room.getCurrentOccupation());
    }

    @Test
    void testDecreaseOccupation() {
        room.increaseOccupation();
        room.increaseOccupation();
        room.decreaseOccupation();
        assertEquals(1, room.getCurrentOccupation());
    }

    @Test
    void testIsOccupied() {
        assertFalse(room.isOccupied());
        room.setOccupied(true);
        assertTrue(room.isOccupied());
    }

    @Test
    void testSetOccupied() {
        room.setOccupied(true);
        assertTrue(room.isOccupied());
    }

    @Test
    void testAddAccess() {
        TypeOfFunction function = TypeOfFunction.DOCTOR;
        room.addAccess(function);
        assertTrue(room.getAccess().contains(function));
    }
}
