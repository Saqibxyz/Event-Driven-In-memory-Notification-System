package notification.service;

import notification.customExceptions.NullObjectException;
import notification.event.CustomEvent;
import notification.subscriber.Subscriber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceManager {
    Map<String, CopyOnWriteArrayList<Subscriber>> eventSubscribersMap = new ConcurrentHashMap<>();
    List<EventLog> history = new CopyOnWriteArrayList<>();
    static ServiceManager serviceManager = new ServiceManager();

    /**
     * @return returns static instance of Service Manager
     */
    public static ServiceManager getInstanceOfServiceManager() {
        return serviceManager;
    }

    /**
     * Publishes event,notifies filtered subscribers and logs event
     *
     * @param event Event to be published
     */
    public void publish(CustomEvent event) {
        if (event == null)
            throw new NullObjectException("Event cannot be null");
        List<Subscriber> list = new ArrayList<>();
        if (!eventSubscribersMap.containsKey(event.getType())) {
            eventSubscribersMap.put(event.getType(), new CopyOnWriteArrayList<>());
        }
        eventSubscribersMap.get(event.getType()).forEach(subscriber -> {
                    if (subscriber.canBeNotified(event)) {
                        subscriber.notifySubscriber(event);
                        list.add(subscriber);
                    }
                }
        );
        history.add(new EventLog(event, list));
    }

    /**
     * Subscribe to an event
     *
     * @param eventName  Name of the event to Subscribe
     * @param subscriber Subscriber who wants to subscribe to this event
     */
    public void subscribe(String eventName, Subscriber subscriber) {
        eventSubscribersMap.computeIfAbsent(eventName, k -> new CopyOnWriteArrayList<>()).add(subscriber);
    }

    public List<Subscriber> getEventSubscribers(String eventType) {
        if (eventSubscribersMap.containsKey(eventType)) {
            return eventSubscribersMap.get(eventType);
        }
        return Collections.emptyList();
    }

    /**
     * Get events that happened in the last given number of hours
     *
     * @param hour the number of hours to look back from the current time
     * @return List containing events happened in previous hour
     */
    public List<EventLog> getPastEvents(int hour) {
        LocalDateTime dateTimeBeforeHour = LocalDateTime.now().minusHours(hour);
        return history.stream().filter(eventLog ->
                eventLog.event.getTimeStamp().isAfter(dateTimeBeforeHour)
        ).toList();

    }

    /**
     * Checks whether event exists
     *
     * @param eventName event to be checked whether it exists
     * @return return true if exists, false otherwise
     */
    public boolean eventExists(String eventName) {
        return eventSubscribersMap.containsKey(eventName);
    }

}
