package notification.subscriber;

import notification.event.CustomEvent;
import notification.exceptions.InvalidEventException;
import notification.exceptions.NullObjectException;
import notification.service.ServiceManager;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {
    static class TestEvent extends CustomEvent {
        protected TestEvent(String details, boolean highPriority) {
            super(details, highPriority);
        }

        @Override
        public String getType() {
            return "TestEvent";
        }
    }


    @Test
    void testConstructorThrowsExceptionForInvalidInputs() {
        assertThrows(NullObjectException.class,
                () -> new Subscriber("", true, false, LocalTime.of(9, 0), LocalTime.of(17, 0)));

        assertThrows(NullObjectException.class,
                () -> new Subscriber("Saqib", true, false, null, LocalTime.of(17, 0)));

        assertThrows(NullObjectException.class,
                () -> new Subscriber("Saqib", true, false, LocalTime.of(9, 0), null));
    }


    @Test
    void testCanBeNotifiedWithoutPreferences() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(8, 0), LocalTime.of(20, 0));

        CustomEvent event = new TestEvent("Event1", false);
        assertTrue(subscriber.canBeNotified(event));
    }

    @Test
    void testCanBeNotifiedHighPriorityPreference() {
        Subscriber subscriber = new Subscriber("Saqib", true, false,
                LocalTime.of(8, 0), LocalTime.of(20, 0));

        CustomEvent normalEvent = new TestEvent("Normal", false);
        CustomEvent highPriorityEvent = new TestEvent("Urgent", true);

        assertFalse(subscriber.canBeNotified(normalEvent));
        assertTrue(subscriber.canBeNotified(highPriorityEvent));
    }

    @Test
    void testCanBeNotifiedWorkingHoursPreference() {
        LocalTime now = LocalTime.now();

        Subscriber subscriber = new Subscriber("Saqib", false, true,
                now.minusHours(1), now.plusHours(1));
        CustomEvent event = new TestEvent("Task", false);

        assertTrue(subscriber.canBeNotified(event));
    }

    @Test
    void testCanBeNotifiedOutsideWorkingHours() {
        LocalTime now = LocalTime.now();

        Subscriber subscriber = new Subscriber("Saqib", false, true,
                now.plusHours(1), now.plusHours(2));

        CustomEvent event = new TestEvent("Task", false);

        assertFalse(subscriber.canBeNotified(event));
    }

    @Test
    void testSubscribeToInvalidEvent() {
        ServiceManager serviceManager = ServiceManager.getInstanceOfServiceManager();

        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(9, 0), LocalTime.of(17, 0));

        assertThrows(InvalidEventException.class,
                () -> serviceManager.subscribe("RandomEvent", subscriber));
    }
}
