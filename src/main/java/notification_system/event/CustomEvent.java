package notification_system.event;

import java.time.LocalDateTime;
import java.util.InputMismatchException;

public abstract class CustomEvent {
    String details;
    boolean hasHighPriority = false;
    final LocalDateTime timeStamp;

    /**
     * @param details         details about event
     * @param hasHighPriority whether event has high priority or not
     */
    protected CustomEvent(String details, boolean hasHighPriority) {
        if (details == null || details.isEmpty())
            throw new InputMismatchException("Please enter valid input. Details cannot be null");
        this.details = details;
        this.hasHighPriority = hasHighPriority;
        timeStamp = LocalDateTime.now();
    }

    /**
     * @return returns Event details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @return Returns type of the Event
     */
    public abstract String getType();

    /**
     * @return returns true if the Event has high priority, false otherwise
     */
    public boolean hasHighPriority() {
        return hasHighPriority;
    }

    /**
     * @return returns time stamp of creation of the event
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
