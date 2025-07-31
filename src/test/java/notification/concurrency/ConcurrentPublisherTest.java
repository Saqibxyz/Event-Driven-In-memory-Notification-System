package notification.concurrency;

import notification.event.ReminderEvent;
import notification.publisher.EventPublisher;
import notification.service.ServiceManager;
import notification.subscriber.Subscriber;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentPublisherTest {

    @Test
    void ConcurrentTest() throws InterruptedException {
        ServiceManager serviceManager = ServiceManager.getInstanceOfServiceManager();
        Subscriber s1 = new Subscriber("Saqib", true, false,
                LocalTime.of(9, 0), LocalTime.of(18, 0));
        Subscriber s2 = new Subscriber("Yawar", false, true,
                LocalTime.of(8, 30), LocalTime.of(17, 30));
        Subscriber s3 = new Subscriber("Shahid", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        serviceManager.subscribe("NewTaskEvent", s1);
        serviceManager.subscribe("ReminderEvent", s2);
        serviceManager.subscribe("OtherEvent", s3);

        EventPublisher publisher = new EventPublisher();


        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            int index = i;
            threads[i] = new Thread(() -> {
                if (index % 2 == 0) {
                    publisher.publishNewTaskEvent("Task " + index, true);
                } else {
                    // add any random event, for this we have to use serviceManager
                    serviceManager.publish(new ReminderEvent("Other Task " + index, false));
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        assertEquals(5, serviceManager.getPastEvents(100).size(),
                "Total 5 events should be published in history");
        assertTrue(serviceManager.eventExists("NewTaskEvent"));
    }
}

