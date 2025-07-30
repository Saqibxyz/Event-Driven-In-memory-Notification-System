package notification_system.publisher;

import notification_system.customExceptions.NullObjectException;
import notification_system.event.NewTaskEvent;
import notification_system.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventPublisher {
    Logger logger = LoggerFactory.getLogger(EventPublisher.class);

    /**
     * Publishes NewTaskEvent
     *
     * @param details              Details of the event
     * @param isHavingHighPriority Whether event is of high priority or not
     */
    public void publishNewTaskEvent(String details, boolean isHavingHighPriority) throws NullObjectException {
        if (details == null || details.isEmpty()) {
            throw new NullObjectException("Please enter valid details");

        }
        ServiceManager.getInstanceOfServiceManager().publish(new NewTaskEvent(details, isHavingHighPriority));

    }
}
