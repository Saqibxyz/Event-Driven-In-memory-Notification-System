package org.notification.system.event;

import java.time.LocalDateTime;

abstract public class CustomEvent {
    int id;
    String details;
    boolean highPriority = false;
    final LocalDateTime timeStamp = LocalDateTime.now();

    public CustomEvent(int id, String details, boolean highPriority) {
        this.id = id;
        this.details = details;
        this.highPriority = highPriority;
    }

    abstract public String getType();

    public boolean hasHighPriority() {
        return highPriority;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
