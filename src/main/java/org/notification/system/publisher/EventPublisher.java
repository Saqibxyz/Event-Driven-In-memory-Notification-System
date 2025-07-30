package org.notification.system.publisher;

import org.notification.system.core.ServiceManager;
import org.notification.system.event.NewTaskEvent;

public class EventPublisher {
    public void publishNewTaskEvent(String details, boolean isHavingHighPriority) {
        ServiceManager.getInstanceOfServiceManager().publish(new NewTaskEvent(details, isHavingHighPriority));
    }
}
