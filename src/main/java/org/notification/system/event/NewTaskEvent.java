package org.notification.system.event;

public class NewTaskEvent extends CustomEvent {
    /**
     * @param details      Details about the event
     * @param highPriority Priority of the event
     */
    public NewTaskEvent(String details, boolean highPriority) {
        super(details, highPriority);
    }

    /**
     * @return returns type of the event
     */
    @Override
    public String getType() {
        return "NewTaskEvent";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        return obj.getClass().getSimpleName().equals("NewTaskEvent");

    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }
}
