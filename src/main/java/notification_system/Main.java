package notification_system;

import notification_system.admin.Admin;
import notification_system.customExceptions.NullObjectException;
import notification_system.publisher.EventPublisher;
import notification_system.publisher.EventReminder;
import notification_system.service.ServiceManager;
import notification_system.subscriber.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ServiceManager serviceManager = ServiceManager.getInstanceOfServiceManager();
        EventReminder eventReminder = new EventReminder();
        EventPublisher eventPublisher = new EventPublisher();
        Admin admin = new Admin();
        // subscribers
        Subscriber subscriber1 = new Subscriber("Saqib",
                false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        Subscriber subscriber2 = new Subscriber("Yawar",
                true, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        Subscriber subscriber3 = new Subscriber("Mohsin",
                false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        // subscribe to events
        serviceManager.subscribe("NewTaskEvent", subscriber1);
        serviceManager.subscribe("NewTaskEvent", subscriber2);
        serviceManager.subscribe("ReminderEvent", subscriber3);
        // publish ReminderTask
        eventReminder.publishReminder();
        // publish NewEventTask

        try {
            eventPublisher.publishNewTaskEvent("review project", false);
            eventPublisher.publishNewTaskEvent("handle exceptions", true);
        } catch (NullObjectException e) {
            logger.error(e.getMessage());
        }
        admin.viewPastEvents(1);


    }
}