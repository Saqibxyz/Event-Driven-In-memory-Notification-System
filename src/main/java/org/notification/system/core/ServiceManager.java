package org.notification.system.core;

import org.notification.system.event.CustomEvent;
import org.notification.system.subscriber.Subscriber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceManager {
    Map<String, CopyOnWriteArrayList<Subscriber>> subscribers = new ConcurrentHashMap<>();
    List<EventLog> history = new ArrayList<>();
    static ServiceManager serviceManager = new ServiceManager();

    public static ServiceManager getInstanceOfServiceManager() {
        return serviceManager;
    }

    public void publish(CustomEvent event) {
        List<Subscriber> list = new ArrayList<>();
        subscribers.get(event.getType()).forEach(subscriber -> {

                    if (subscriber.canBeNotified(event)) {
                        subscriber.notifySubscriber(event);
                        list.add(subscriber);
                    }
                }
        );
        history.add(new EventLog(event, list));
    }

    public void subscribe(String eventName, Subscriber subscriber) {
        subscribers.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>()).add(subscriber);
    }

    public List<EventLog> getPastEvents(int hour) {
        LocalDateTime dateTimeBeforeHour = LocalDateTime.now().minusHours(hour);
        return history.stream().filter(eventLog ->
                eventLog.event.getTimeStamp().isAfter(dateTimeBeforeHour)
        ).toList();

    }

}
