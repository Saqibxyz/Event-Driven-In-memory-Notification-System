package notification.service;

import notification.customExceptions.InvalidEventException;
import notification.customExceptions.NullObjectException;
import notification.event.CustomEvent;
import notification.event.NewTaskEvent;
import notification.event.ReminderEvent;
import notification.subscriber.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

    ServiceManager serviceManager;

    @BeforeEach
    void setup() {

        serviceManager = ServiceManager.getInstanceOfServiceManager();
        serviceManager.eventSubscribersMap.clear();
        serviceManager.history.clear();
        serviceManager.eventSubscribersMap.put("NewTaskEvent", new CopyOnWriteArrayList<>());
        serviceManager.eventSubscribersMap.put("ReminderEvent", new CopyOnWriteArrayList<>());
    }

    @Test
    void testSubscribeValidEventAndEventExists() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        serviceManager.subscribe("NewTaskEvent", subscriber);

        assertTrue(serviceManager.eventExists("NewTaskEvent"));
        assertEquals(1, serviceManager.eventSubscribersMap.get("NewTaskEvent").size());
    }

    @Test
    void testSubscribeInvalidEventThrowsException() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        assertThrows(InvalidEventException.class,
                () -> serviceManager.subscribe("TestEvent", subscriber));
    }

    @Test
    void testPublishAddsEventToHistory() {
        CustomEvent event = new NewTaskEvent("Hello", false);
        serviceManager.publish(event);

        List<EventLog> pastEvents = serviceManager.getPastEvents(1);
        assertEquals(1, pastEvents.size());
        assertEquals(event, pastEvents.getFirst().event);
    }

    @Test
    void testPublishNotifiesSubscribers() {
        Subscriber subscriber = new Subscriber("Saqib", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        serviceManager.subscribe("NewTaskEvent", subscriber);
        CustomEvent event = new NewTaskEvent("Test code", false);

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
        CustomEvent event1 = new NewTaskEvent("Old Event", false);
        serviceManager.publish(event1);

        CustomEvent event2 = new ReminderEvent("New Event", true);
        serviceManager.publish(event2);

        List<EventLog> recentEvents = serviceManager.getPastEvents(1);
        assertEquals(2, recentEvents.size());
    }
}

