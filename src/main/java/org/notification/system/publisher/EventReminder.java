package org.notification.system.publisher;

import org.notification.system.core.ServiceManager;
import org.notification.system.event.ReminderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventReminder {

    ScheduledExecutorService executor;
    Logger logger = LoggerFactory.getLogger(EventReminder.class);

    public void publishReminder() {
        executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> ServiceManager.getInstanceOfServiceManager().publish(new ReminderEvent("This is a reminder", true));
        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }

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
