package notification.service;

import notification.event.CustomEvent;
import notification.exceptions.NullObjectException;
import notification.subscriber.Subscriber;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventLog {
    CustomEvent event;
    List<Subscriber> subs;

    /**
     * @param event Event
     * @param subs  List of subscribers notified by the event
     */
    public EventLog(CustomEvent event, List<Subscriber> subs) {
        if (event == null)
            throw new NullObjectException("Event cannot be null");
        this.event = event;
        this.subs = subs;
    }

    /**
     * @return Returns list of subscribers of the event
     */
    public List<Subscriber> getSubs() {
        return subs;
    }

    @Override
    public String toString() {
        return "[" + event.getTimeStamp().format(DateTimeFormatter.
                ofPattern("dd/MM/yy hh:mm")) + "] " + event.getType() + " - " +
                event.getDetails() + " : " + subs.stream()
                .map(Subscriber::toString).toList();
    }
}
