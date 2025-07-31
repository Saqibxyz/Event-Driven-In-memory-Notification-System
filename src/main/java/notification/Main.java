package notification;

import notification.admin.Admin;
import notification.publisher.EventPublisher;
import notification.publisher.EventReminder;
import notification.service.ServiceManager;
import notification.subscriber.Subscriber;

import java.time.LocalTime;

public class Main {

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

        eventPublisher.publishNewTaskEvent("review project", false);
        eventPublisher.publishNewTaskEvent("handle exceptions", true);

        admin.viewPastEvents(1);


    }
}