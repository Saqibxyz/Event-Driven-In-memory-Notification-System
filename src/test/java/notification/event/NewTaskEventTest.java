package notification.event;

import notification.customExceptions.NullObjectException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewTaskEventTest {

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        NewTaskEvent event = new NewTaskEvent("Finish project", true);

        assertEquals("Finish project", event.getDetails());
        assertTrue(event.hasHighPriority());
        assertNotNull(event.getTimeStamp());
    }

    @Test
    void testConstructorThrowsExceptionWhenDetailsIsNull() {
        assertThrows(NullObjectException.class, () -> new NewTaskEvent(null, true));
    }

    @Test
    void testConstructorThrowsExceptionWhenDetailsIsEmpty() {
        assertThrows(NullObjectException.class, () -> new NewTaskEvent("", false));
    }

    @Test
    void testGetTypeReturnsCorrectValue() {
        NewTaskEvent event = new NewTaskEvent("Task A", false);
        assertEquals("NewTaskEvent", event.getType());
    }

    @Test
    void testHasHighPriorityReturnsCorrectValue() {
        NewTaskEvent event1 = new NewTaskEvent("Low priority", false);
        NewTaskEvent event2 = new NewTaskEvent("High priority", true);

        assertFalse(event1.hasHighPriority());
        assertTrue(event2.hasHighPriority());
    }

    @Test
    void testEqualsSameObject() {
        NewTaskEvent event = new NewTaskEvent("Task", true);
        assertTrue(event.equals(event));
    }

    @Test
    void testEqualsNullObject() {
        NewTaskEvent event = new NewTaskEvent("Task", false);
        assertNotEquals(null, event);
    }

    @Test
    void testEqualsDifferentClass() {
        NewTaskEvent event = new NewTaskEvent("Task", false);
        Object other = new Object();
        assertNotEquals(event, other);
    }

    @Test
    void testEqualsSameClassDifferentInstances() {
        NewTaskEvent event1 = new NewTaskEvent("Task 1", true);
        NewTaskEvent event2 = new NewTaskEvent("Task 2", false);
        assertEquals(event1, event2);
    }


    @Test
    void testHashCodeMatchesTypeHashCode() {
        NewTaskEvent event = new NewTaskEvent("Task", false);
        assertEquals("NewTaskEvent".hashCode(), event.hashCode());
    }

    @Test
    void testTimeStampIsSetAtCreation() {
        LocalDateTime before = LocalDateTime.now();
        NewTaskEvent event = new NewTaskEvent("Task", true);
        LocalDateTime after = LocalDateTime.now();

        assertTrue(!event.getTimeStamp().isBefore(before) &&
                !event.getTimeStamp().isAfter(after));
    }
}
