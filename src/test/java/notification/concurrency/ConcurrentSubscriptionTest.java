package notification.concurrency;

import notification.service.ServiceManager;
import notification.subscriber.Subscriber;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentSubscriptionTest {


    @Test
    public void testConcurrentSubscription() {
        ServiceManager serviceManager = new ServiceManager();// for testing


        Subscriber s1 = new Subscriber("Saqib", true, false,
                LocalTime.of(9, 0), LocalTime.of(18, 0));

        Subscriber s2 = new Subscriber("Yawar", false, true,
                LocalTime.of(8, 30), LocalTime.of(17, 30));

        Subscriber s3 = new Subscriber("Shahid", false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        Subscriber s4 = new Subscriber("Mohsin", true, true,
                LocalTime.of(7, 0), LocalTime.of(15, 0));

        Subscriber s5 = new Subscriber("Faheem", false, false,
                LocalTime.of(11, 0), LocalTime.of(20, 0));

        ArrayList<Subscriber> list = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, s5));
        String[] events = {"NewTaskEvent", "OtherEvent"};

        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            int index = i;
            threads[i] = new Thread(() ->
                    serviceManager.subscribe(events[index % 2], list.get(index)));
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // just testing
            }
        }
        assertEquals(3, serviceManager.getEventSubscribers("NewTaskEvent").size());
        assertEquals(2, serviceManager.getEventSubscribers("OtherEvent").size());
    }

}


