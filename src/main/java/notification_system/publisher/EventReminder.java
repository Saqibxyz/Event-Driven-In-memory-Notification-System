package notification_system.publisher;

import notification_system.customExceptions.NullObjectException;
import notification_system.event.ReminderEvent;
import notification_system.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventReminder {

    ScheduledExecutorService executor;
    Logger logger = LoggerFactory.getLogger(EventReminder.class);

    /**
     * Publishes ReminderEvent after every 10 seconds continuously
     */
    public void publishReminder() throws RuntimeException {
        executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                ServiceManager.getInstanceOfServiceManager().publish(new ReminderEvent("This is a reminder", true));
            } catch (NullObjectException e) {
                throw new RuntimeException(e);
            }
        };
        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Stops Publishing ReminderEvent
     */
    public void stopReminder() {
        if (executor == null) {
            return;
        }
        try {
            executor.shutdown();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
