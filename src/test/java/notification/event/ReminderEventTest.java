package notification.event;

import notification.customExceptions.NullObjectException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReminderEventTest {

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        ReminderEvent event = new ReminderEvent("Send report", true);

        assertEquals("Send report", event.getDetails());
        assertTrue(event.hasHighPriority());
        assertNotNull(event.getTimeStamp());
    }

    @Test
    void testConstructorThrowsExceptionWhenDetailsIsNull() {
        assertThrows(NullObjectException.class, () -> new ReminderEvent(null, true));
    }

    @Test
    void testConstructorThrowsExceptionWhenDetailsIsEmpty() {
        assertThrows(NullObjectException.class, () -> new ReminderEvent("", false));
    }

    @Test
    void testGetTypeReturnsCorrectValue() {
        ReminderEvent event = new ReminderEvent("Check emails", false);
        assertEquals("ReminderEvent", event.getType());
    }

    @Test
    void testHasHighPriority() {
        ReminderEvent event1 = new ReminderEvent("Normal priority", false);
        ReminderEvent event2 = new ReminderEvent("Urgent", true);

        assertFalse(event1.hasHighPriority());
        assertTrue(event2.hasHighPriority());
    }

    @Test
    void testEqualsSameObject() {
        ReminderEvent event = new ReminderEvent("Meeting", true);
        assertTrue(event.equals(event));
    }

    @Test
    void testEqualsNullObject() {
        ReminderEvent event = new ReminderEvent("Task", false);
        assertNotEquals(null, event);
    }

    @Test
    void testEqualsDifferentClass() {
        ReminderEvent event = new ReminderEvent("Task", false);
        NewTaskEvent event2 = new NewTaskEvent("Task", false);
        Object other = new Object();
        assertNotEquals(event, other);
        assertNotEquals(event, event2);
    }

    @Test
    void testEqualsAnotherReminderEvent() {
        ReminderEvent event1 = new ReminderEvent("Task 1", true);
        ReminderEvent event2 = new ReminderEvent("Task 2", false);
        assertEquals(event1, event2);
    }


    @Test
    void testHashCode() {
        ReminderEvent event = new ReminderEvent("Task", true);
        int hash = event.hashCode();
        assertEquals(hash, event.hashCode());
    }

    @Test
    void testHashCodeMatchesTypeHashCode() {
        ReminderEvent event = new ReminderEvent("Task", false);
        assertEquals("ReminderEvent".hashCode(), event.hashCode());
    }

    @Test
    void testTimeStampIsSetAtCreation() {
        LocalDateTime before = LocalDateTime.now();
        ReminderEvent event = new ReminderEvent("Task", true);
        LocalDateTime after = LocalDateTime.now();

        assertTrue(!event.getTimeStamp().isBefore(before) &&
                !event.getTimeStamp().isAfter(after));
    }
}

