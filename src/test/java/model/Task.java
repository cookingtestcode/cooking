package model;

import java.time.LocalDateTime;

public class Task {
    private String name;
    private String type;
    private LocalDateTime deadline;

    public Task(String name, String type, LocalDateTime deadline) {
        this.name = name;
        this.type = type;
        this.deadline = deadline;
    }

    public Task(String name, String type) {
        this(name, type, null);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task)) return false;
        Task other = (Task) o;
        return name.equals(other.name) && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + type.hashCode();
    }
}