package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Chef {
    private String name;
    private List<String> expertise;
    private List<Task> tasks = new ArrayList<>();  // تحديد النوع List<Task>
    private List<String> notifications = new ArrayList<>();  // تحديد النوع List<String>

    public Chef(String name, List<String> expertise, int initialTasks) {
        this.name = name;
        this.expertise = expertise;

        for (int i = 0; i < initialTasks; ++i) {
            this.tasks.add(new Task("Task" + i, expertise.get(0)));  // لا حاجة لتحويل expertise.get(0) إلى String
        }
    }

    public void assignTask(Task task) {
        this.tasks.add(task);
        this.notifications.add("New Task Assigned: " + task.getName() +
                (task.getDeadline() != null ? " (Due: " + task.getDeadline() + ")" : ""));
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

    public String getName() {  // تعديل النوع إلى String
        return this.name;
    }

    private LocalDateTime scheduledTaskTime; // لحفظ وقت المهمة

    public void scheduleCookingTask(LocalDateTime taskTime) {
        this.scheduledTaskTime = taskTime;
    }

    public LocalDateTime getScheduledTaskTime() {
        return scheduledTaskTime;
    }
}
