package org.notification.system.event;

import java.time.LocalDateTime;

public abstract class CustomEvent {
    String details;
    boolean hasHighPriority = false;
    final LocalDateTime timeStamp = LocalDateTime.now();

    protected CustomEvent(String details, boolean hasHighPriority) {
        this.details = details;
        this.hasHighPriority = hasHighPriority;
    }

    public String getDetails() {
        return details;
    }

    public abstract String getType();

    public boolean hasHighPriority() {
        return hasHighPriority;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
