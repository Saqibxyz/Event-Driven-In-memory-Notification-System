package org.notification.system.core;

import org.notification.system.event.CustomEvent;
import org.notification.system.subscriber.Subscriber;


public class EventLog {
    CustomEvent event;
    Subscriber subs;

    public EventLog(CustomEvent event, Subscriber subs) {
        this.event = event;
        this.subs = subs;
    }

    @Override
    public String toString() {
        return event.getType() + "->" + subs.getName();
    }
}
