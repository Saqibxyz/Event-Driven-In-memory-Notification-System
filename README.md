# Event-Driven-In-memory-Notification-System (JAVA)

This is a **modular**, **threaded**, and **in-memory** event-notification backend system built in **Java**, which mimics
notification workflows similar to systems like Google Calendar. It supports:

- Event publishing
- Subscriber management and filtering (priority/time)
- Scheduled event reminders
- Event history tracking
- Clean code and tested via JUnit 5

---

## System Design (What’s Happening)

At the core of the system lies the `ServiceManager`, which takes care of:

- **Subscription management:** Registering which subscriber wants what event type.
- **Publishing:** Routes events to only those subscribers who want them, based on:
    - Priority filter (high/low)
    - Time filter (working hours)
- **Event history:** Keeps past events and delivers them to admin on demand.

Other key roles:

- `EventPublisher`: Manually creates & pushes _**NewTaskEvent**_ events.
- `EventReminder`: Fires **_ReminderEvent_** events every 10 seconds (via ScheduledExecutorService).
- `Subscriber`: Has logic to decide if it wants to receive a certain event.
- `Admin`: Can ask the system to list all events in the past X hours.

The image below shows how it all flows:

![System Design](SystemDesign.png)

---

## Project Structure

```
src
├── main
│   └── java
│       └── notification_system
│           ├── admin
│           │   └── Admin.java
│           ├── customExceptions
│           │   └── NullObjectException.java
│           ├── event
│           │   ├── CustomEvent.java
│           │   ├── NewTaskEvent.java
│           │   └── ReminderEvent.java
│           ├── publisher
│           │   ├── EventPublisher.java
│           │   └── EventReminder.java
│           ├── service
│           │   ├── EventLog.java
│           │   └── ServiceManager.java
│           ├── subscriber
│           │   └── Subscriber.java
│           └── Main.java
└── test
    └── java
        └── org.notification.system.admin
            └── AdminTest.java (sample location)
```

---

## How to Run the App

Make sure you have Maven and Java 17+ installed.

### Step-by-step:

```bash
git clone https://github.com/Saqibxyz/Event-Driven-In-memory-Notification-System.git
 cd .\Event-Driven-In-memory-Notification-System\
```

1. **Compile the code**

```bash
mvn clean compile
```

2. **Run the application**

```bash
mvn exec:java
```

#### Make sure your `pom.xml` has below plugin for the above command to work

```
 <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
      <configuration>
          <mainClass>notification_system.Main</mainClass>
      </configuration>
 </plugin>
```

---

## How to Run Tests

```bash
mvn test
```

---

## Example Run

```bash
[notification_system.Main.main()] INFO notification_system.subscriber.Subscriber - Saqib  received NewTaskEvent at 31/07/25 12:35:58
[pool-1-thread-1] INFO notification_system.subscriber.Subscriber - Mohsin  received ReminderEvent at 31/07/25 12:35:58
[notification_system.Main.main()] INFO notification_system.subscriber.Subscriber - Saqib  received NewTaskEvent at 31/07/25 12:35:58
[notification_system.Main.main()] INFO notification_system.subscriber.Subscriber - Yawar  received NewTaskEvent at 31/07/25 12:35:58
[notification_system.Main.main()] INFO notification_system.admin.Admin - [31/07/25 12:35] NewTaskEvent - review project : [Saqib]
[notification_system.Main.main()] INFO notification_system.admin.Admin - [31/07/25 12:35] ReminderEvent - This is a reminder : [Mohsin]
[notification_system.Main.main()] INFO notification_system.admin.Admin - [31/07/25 12:35] NewTaskEvent - handle exceptions : [Saqib, Yawar]
```

- The ReminderEvent fires every 10 seconds.
- Subscribers are filtered according to:
    - working hours (`LocalTime`)
    - preference flags (`isPreferringHighPriorityEvents`, `isPreferringEventsDuringWorkingHours`)

---

## Notable Features

- **Singleton ServiceManager** (one event bus)
- **Event filtering** based on real-time conditions
- **Multithreading** via `ScheduledExecutorService` for timed events
- **EventLog** tracking and stream-filtered history

---

## Credits

Made by **_Saqib Ayoub_**  
Designed for performance, modularity, and future extensibility.
---

## License

Free to use, modify, or contribute.


