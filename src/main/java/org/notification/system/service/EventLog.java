package org.notification.system.service;

import org.notification.system.event.CustomEvent;
import org.notification.system.subscriber.Subscriber;

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
