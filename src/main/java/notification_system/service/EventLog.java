package notification_system.service;

import notification_system.customExceptions.NullObjectException;
import notification_system.event.CustomEvent;
import notification_system.subscriber.Subscriber;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventLog {
    CustomEvent event;
    List<Subscriber> subs;

    /**
     * @param event Event
     * @param subs  List of subscribers notified by the event
     */
    public EventLog(CustomEvent event, List<Subscriber> subs) throws NullObjectException {
        if (event == null)
            throw new NullObjectException("Event cannot be null");
        this.event = event;
        this.subs = subs;
    }

    @Override
    public String toString() {
        return "[" + event.getTimeStamp().format(DateTimeFormatter.
                ofPattern("dd/MM/yy hh:mm")) + "] " + event.getType() + " - " +
                event.getDetails() + " : " + subs.stream()
                .map(Subscriber::toString).toList();
    }
}
