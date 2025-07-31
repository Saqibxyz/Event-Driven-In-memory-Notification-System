package notification.event;

import notification.customExceptions.NullObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomEventTest {
    static class TestEvent extends CustomEvent {
        protected TestEvent(String details, boolean hasHighPriority) {

            super(details, hasHighPriority);

        }

        @Override
        public String getType() {
            return "TestEvent";
        }

    }

    @Test
    @DisplayName("EventConstructor")
    void testConstructorOfCustomEvent() {
        assertThrows(NullObjectException.class, () -> {
            new TestEvent(null, true);
        });


    }

    @Test
    @DisplayName("TestEventType")
    void testEventType() {
        TestEvent event = new TestEvent("Details", true);
        assertEquals("TestEvent", event.getType());
    }

    @Test
    @DisplayName("TestEventDetails")
    void testEventDetails() {
        TestEvent event = new TestEvent("Details", true);
        assertEquals("Details", event.getDetails());
    }
}
