package model;


import java.util.ArrayList;
import java.util.List;

public class Chef {
    private String name;
    private List<String> expertise;
    private List<Task> tasks = new ArrayList();
    private List<String> notifications = new ArrayList();

    public Chef(String name, List<String> expertise, int initialTasks) {
        this.name = name;
        this.expertise = expertise;

        for(int i = 0; i < initialTasks; ++i) {
            this.tasks.add(new Task("Task" + i, (String)expertise.get(0)));
        }


    }
    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public void assignTask(Task task) {
        this.tasks.add(task);
        List var10000 = this.notifications;
        String var10001 = task.getName();
        var10000.add("New Task Assigned: " + var10001 + (task.getDeadline() != null ? " (Due: " + String.valueOf(task.getDeadline()) + ")" : ""));
    }

    public List<String> getExpertise() {
        return this.expertise;
    }

    public int getWorkload() {
        return this.tasks.size();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<String> getNotifications() {
        return this.notifications;
    }

    public Object getName() {
        return this.name;
    }
    public void scheduleCookingTask(LocalDateTime time) {
    Task scheduledTask = new Task("Scheduled Cooking Task", "General");
    scheduledTask.setDeadline(time);
    this.tasks.add(scheduledTask);
    this.notifications.add("Cooking Task scheduled for: " + time);
}
    public LocalDateTime getScheduledTaskTime() {
    return this.tasks.stream()
        .filter(task -> task.getDeadline() != null)
        .map(Task::getDeadline)
        .findFirst()
        .orElse(null);
}
}
