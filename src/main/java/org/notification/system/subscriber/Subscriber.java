package org.notification.system.subscriber;

import org.notification.system.event.CustomEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Subscriber {
    String name;
    boolean isPreferringHighPriorityEvents = false;
    boolean isPreferringEventsDuringWorkingHours = false;
    LocalTime workingHourStart;
    LocalTime workingHourEnd;
    Logger logger = LoggerFactory.getLogger(Subscriber.class);

    Subscriber(String name) {
        this.name = name;
    }

    public Subscriber(String name, boolean isPreferringHighPriorityEvents, boolean isPreferringEventsDuringWorkingHours,
                      LocalTime workingHourStart, LocalTime workingHourEnd

    ) {
        this.name = name;
        this.isPreferringHighPriorityEvents = isPreferringHighPriorityEvents;
        this.isPreferringEventsDuringWorkingHours = isPreferringEventsDuringWorkingHours;
        this.workingHourStart = workingHourStart;
        this.workingHourEnd = workingHourEnd;
    }

    public String getName() {
        return name;
    }

    public void notifySubscriber(CustomEvent event) {
        logger.info("{}  received {} at {}", name, event.getType(), event.getTimeStamp().format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mm")));

    }

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
        return this.name;
    }

}
