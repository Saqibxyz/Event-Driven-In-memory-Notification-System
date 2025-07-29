package org.notification.system.event;

public class NewTaskEvent extends Event {
    public NewTaskEvent(int id, String details) {
        super(id, details);
    }

    @Override
    public String getType() {
        return "newTaskEvent";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!obj.getClass().getSimpleName().equals("NewTaskEvent")) return false;
        HeartBeatEvent other = (HeartBeatEvent) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
