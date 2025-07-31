package notification.admin;

import notification.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin {
    Logger logger = LoggerFactory.getLogger(Admin.class);

    /**
     * Displays all events that happened in the last given number of hours.
     *
     * @param hour the number of hours to look back from the current time
     */
    public void viewPastEvents(int hour) {
        ServiceManager.getInstanceOfServiceManager().getPastEvents(hour).forEach(eventLog -> {
            logger.info(eventLog.toString());
        });
    }
}
