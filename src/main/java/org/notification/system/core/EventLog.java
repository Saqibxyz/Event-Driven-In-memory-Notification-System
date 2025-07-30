package org.notification.system.core;

import org.notification.system.event.CustomEvent;
import org.notification.system.subscriber.Subscriber;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class EventLog {
    CustomEvent event;
    List<Subscriber> subs;

    public EventLog(CustomEvent event, List<Subscriber> subs) {
        this.event = event;
        this.subs = subs;
    }

    @Override
    public String toString() {
        return "[" + event.getTimeStamp().format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm")) + "] " + event.getType() + " - " + event.getDetails() + " : " + subs.stream().map(Subscriber::toString).toList();
    }
}
