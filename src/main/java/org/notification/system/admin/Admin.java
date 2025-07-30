package org.notification.system.admin;

import org.notification.system.core.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin {
    Logger logger = LoggerFactory.getLogger(Admin.class);

    public void viewPastEvents(int hour) {
        ServiceManager.getInstanceOfServiceManager().getPastEvents(hour).forEach(eventLog -> {
            logger.info(eventLog.toString());
        });
    }
}
