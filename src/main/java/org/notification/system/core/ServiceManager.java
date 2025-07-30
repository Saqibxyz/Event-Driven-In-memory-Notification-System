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
    static Map<CustomEvent, CopyOnWriteArrayList<Subscriber>> subscribers = new ConcurrentHashMap<>();
    static List<EventLog> history = new ArrayList<>();

    public static void publish(CustomEvent event) {
        subscribers.get(event).forEach(subscriber -> {
                    if (subscriber.canBeNotified(event)) {
                        subscriber.notifySubscriber(event);
                        history.add(new EventLog(event, subscriber));
                    }
                }
        );
    }

    public static void subscribe(CustomEvent event, Subscriber subscriber) {
        subscribers.computeIfAbsent(event, k -> new CopyOnWriteArrayList<>()).add(subscriber);
    }

    public static List<EventLog> getPastEvents(int hour) {
        LocalDateTime dateTimeBeforeHour = LocalDateTime.now().minusHours(hour);
        return history.stream().filter(eventLog ->
                eventLog.event.getTimeStamp().isAfter(dateTimeBeforeHour)
        ).toList();

    }

}
