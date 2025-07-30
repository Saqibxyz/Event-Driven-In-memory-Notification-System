package org.notification.system.event;

public class ReminderEvent extends CustomEvent {
    public ReminderEvent(String details, boolean highPriority) {
        super(details, highPriority);
    }

    @Override
    public String getType() {
        return "ReminderEvent";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        return obj.getClass().getSimpleName().equals("HeartBeatEvent");
    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }
}
