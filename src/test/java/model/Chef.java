package model;

import java.util.*;

public class Chef {
    private String name;
    private List<String> expertise;
    private List<Task> tasks = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    public Chef(String name, List<String> expertise, int initialTasks) {
        this.name = name;
        this.expertise = expertise;
        for (int i = 0; i < initialTasks; i++) {
            tasks.add(new Task("Task" + i, expertise.get(0)));
        }
    }

    public void assignTask(Task task) {
        tasks.add(task);
        notifications.add("New Task Assigned: " + task.getName() +
                (task.getDeadline() != null ? " (Due: " + task.getDeadline() + ")" : ""));
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public int getWorkload() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public Object getName() {
        return name;
    }
}