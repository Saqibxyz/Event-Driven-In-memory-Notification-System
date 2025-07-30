package org.notification.system;

import org.notification.system.admin.Admin;
import org.notification.system.core.ServiceManager;
import org.notification.system.publisher.EventPublisher;
import org.notification.system.publisher.EventReminder;
import org.notification.system.subscriber.Subscriber;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        ServiceManager serviceManager = ServiceManager.getInstanceOfServiceManager();
        EventReminder eventReminder = new EventReminder();
        EventPublisher eventPublisher = new EventPublisher();
        Admin admin = new Admin();
        Subscriber subscriber1 = new Subscriber("Saqib",
                false, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        Subscriber subscriber2 = new Subscriber("Yawar",
                true, false,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        Subscriber subscriber3 = new Subscriber("Mohsin",
                false, true,
                LocalTime.of(10, 0), LocalTime.of(19, 0));
        serviceManager.subscribe("NewTaskEvent", subscriber1);
        serviceManager.subscribe("NewTaskEvent", subscriber2);
        serviceManager.subscribe("ReminderEvent", subscriber3);
        eventReminder.publishReminder();
        eventPublisher.publishNewTaskEvent("review project", false);
        eventPublisher.publishNewTaskEvent("handle exceptions", true);
        admin.viewPastEvents(1);


    }
}