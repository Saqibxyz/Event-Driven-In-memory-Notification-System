package notification.service;

import notification.customExceptions.NullObjectException;
import notification.event.CustomEvent;
import notification.subscriber.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

    ServiceManager serviceManager;

    static class TestEvent extends CustomEvent {
        protected TestEvent(String details, boolean highPriority) {
            super(details, highPriority);
        }

        @Override
        public String getType() {
            return "TestEvent";
        }
    }

    @BeforeEach
    void setup() {
        serviceManager = ServiceManager.getInstanceOfServiceManager();
        serviceManager.eventSubscribersMap.clear();
        serviceManager.history.clear();
    }

    @Test
    void testSubscribeAndEventExists() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        serviceManager.subscribe("TestEvent", subscriber);

        assertTrue(serviceManager.eventExists("TestEvent"));
        assertEquals(1, serviceManager.eventSubscribersMap.get("TestEvent").size());
    }

    @Test
    void testPublishAddsEventToHistory() {
        CustomEvent event = new TestEvent("Hello", false);
        serviceManager.publish(event);

        List<EventLog> pastEvents = serviceManager.getPastEvents(1);
        assertEquals(1, pastEvents.size());
        assertEquals(event, pastEvents.getFirst().event);
    }

    @Test
    void testPublishNotifiesSubscribers() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        serviceManager.subscribe("TestEvent", subscriber);
        CustomEvent event = new TestEvent("Test code", false);

        serviceManager.publish(event);

        List<EventLog> logs = serviceManager.getPastEvents(1);
        assertEquals(1, logs.size());
        assertEquals(1, logs.getFirst().getSubs().size());
    }

    @Test
    void testPublishThrowsExceptionForNullEvent() {
        assertThrows(NullObjectException.class, () -> serviceManager.publish(null));
    }

    @Test
    void testPastEventsReturnsOnlyRecentEvents() {
        CustomEvent event1 = new TestEvent("Old Event", false);
        serviceManager.publish(event1);
        CustomEvent event2 = new TestEvent("New Event", true);
        serviceManager.publish(event2);
        List<EventLog> recentEvents = serviceManager.getPastEvents(1);
        assertEquals(2, recentEvents.size());
    }
}

