package notification.publisher;

import notification.event.NewTaskEvent;
import notification.service.ServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventPublisherTest {
    ServiceManager serviceManager;

    @BeforeEach
    void initialiseServicemanagre() {
        serviceManager = new ServiceManager();
    }

    @Test
    void testPublisherPublishEvent() {
        NewTaskEvent event = new NewTaskEvent("details", true);
        serviceManager.publish(event);
        assertTrue(serviceManager.eventExists(event.getType()));
    }
}
