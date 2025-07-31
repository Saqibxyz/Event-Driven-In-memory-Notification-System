package notification.publisher;

import notification.event.ReminderEvent;
import notification.service.ServiceManager;
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
    public void publishReminder() {
        executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            ServiceManager.getInstanceOfServiceManager().publish(new ReminderEvent("This is a reminder", true));
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
