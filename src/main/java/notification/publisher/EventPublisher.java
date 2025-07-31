package notification.publisher;

import notification.event.NewTaskEvent;
import notification.service.ServiceManager;

public class EventPublisher {


    /**
     * Publishes NewTaskEvent
     *
     * @param details              Details of the event
     * @param isHavingHighPriority Whether event is of high priority or not
     */
    public void publishNewTaskEvent(String details, boolean isHavingHighPriority) {
        ServiceManager.getInstanceOfServiceManager().publish(new NewTaskEvent(details, isHavingHighPriority));

    }
}
