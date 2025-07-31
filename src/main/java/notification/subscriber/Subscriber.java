package notification.subscriber;

import notification.event.CustomEvent;
import notification.exceptions.NullObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Subscriber {
    String id;
    String name;
    boolean isPreferringHighPriorityEvents = false;
    boolean isPreferringEventsDuringWorkingHours = false;
    LocalTime workingHourStart;
    LocalTime workingHourEnd;
    Logger logger = LoggerFactory.getLogger(Subscriber.class);

    /**
     * Not letting use default constructor to avoid bugs
     */
    private Subscriber() {
    }

    /**
     * Initialises Subscriber
     *
     * @param name                                 name of the subscriber
     * @param isPreferringHighPriorityEvents       Does Subscriber prefer receiving notifications of high priority only
     * @param isPreferringEventsDuringWorkingHours Does Subscriber prefer receiving notifications of During working hours only
     * @param workingHourStart                     Start of working hours
     * @param workingHourEnd                       End of working hours
     */
    public Subscriber(String name, boolean isPreferringHighPriorityEvents, boolean isPreferringEventsDuringWorkingHours,
                      LocalTime workingHourStart, LocalTime workingHourEnd

    ) {
        if (name == null || name.isEmpty() || workingHourStart == null || workingHourEnd == null)
            throw new NullObjectException("Invalid input");
        this.id = UUID.randomUUID().toString().substring(0, 6);
        this.name = name;
        this.isPreferringHighPriorityEvents = isPreferringHighPriorityEvents;
        this.isPreferringEventsDuringWorkingHours = isPreferringEventsDuringWorkingHours;
        this.workingHourStart = workingHourStart;
        this.workingHourEnd = workingHourEnd;
    }

    public String getName() {
        return name + "(" + this.id + ")";
    }

    /**
     * Notifies event subscriber
     *
     * @param event Event that user has subscribed to
     */
    public void notifySubscriber(CustomEvent event) {
        logger.info("{}  received {} at {}", getName(), event.getType(), event.getTimeStamp().format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss")));

    }

    /**
     * Checks whether the user has subscribed to this event or not
     *
     * @param event Notifying event
     * @return true if subscribed ,otherwise false
     */
    public boolean canBeNotified(CustomEvent event) {
        LocalTime currentTime = LocalTime.now();
        if (isPreferringHighPriorityEvents && !event.hasHighPriority()) {
            return false;
        }
        if (isPreferringEventsDuringWorkingHours && (workingHourStart.isAfter(currentTime) || currentTime.isAfter(workingHourEnd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.id + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Subscriber other)) return false;
        return this.id.equals(other.id);
    }

}
