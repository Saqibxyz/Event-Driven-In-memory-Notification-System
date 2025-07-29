package org.notification.system.event;

import java.time.LocalDateTime;

abstract class Event {
    int id;
    String details;
    final LocalDateTime timeStamp = LocalDateTime.now();

    public Event(int id, String details) {
        this.id = id;
        this.details = details;
    }

    abstract String getType();

    LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
