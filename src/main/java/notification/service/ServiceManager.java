package notification.service;

import notification.event.CustomEvent;
import notification.exceptions.InvalidEventException;
import notification.exceptions.NullObjectException;
import notification.subscriber.Subscriber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceManager {
    Map<String, CopyOnWriteArrayList<Subscriber>> eventSubscribersMap =
            new ConcurrentHashMap<>() {{
                put("NewTaskEvent", new CopyOnWriteArrayList<>());
                put("ReminderEvent", new CopyOnWriteArrayList<>());
            }};

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
        eventSubscribersMap.computeIfAbsent(event.getType(),
                k -> new CopyOnWriteArrayList<>());

        List<Subscriber> notifiedSubscribers = new ArrayList<>();
        for (Subscriber subscriber : eventSubscribersMap.get(event.getType())) {
            if (subscriber.canBeNotified(event)) {
                subscriber.notifySubscriber(event);
                notifiedSubscribers.add(subscriber);
            }
        }
        history.add(new EventLog(event, notifiedSubscribers));
    }


    /**
     * Subscribe to an event
     *
     * @param eventName  Name of the event to Subscribe
     * @param subscriber Subscriber who wants to subscribe to this event
     */
    public void subscribe(String eventName, Subscriber subscriber) {
        if (!eventExists(eventName)) {
            throw new InvalidEventException("Enter valid Event");
        }
        boolean exists = eventSubscribersMap.get(eventName)
                .stream()
                .anyMatch(subs -> subs.equals(subscriber));

        if (!exists) {
            eventSubscribersMap.get(eventName).add(subscriber);
        }
    }


    /**
     * Retuns List of subscribers of an event
     *
     * @param eventType Event Type
     * @return returns a list containing Subscribers of the event
     */
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
