package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String type;
    private LocalDateTime deadline;

    // Constructor with deadline
    public Task(String name, String type, LocalDateTime deadline) {
        this.name = name;
        this.type = type;
        this.deadline = deadline;
    }

    // Constructor without deadline (deadline will be null)
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

    // Overriding equals method to compare Task objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // check for the same object
        if (o == null || getClass() != o.getClass()) return false; // check for null and class mismatch
        Task task = (Task) o;
        return name.equals(task.name) && type.equals(task.type); // check for equality based on name and type
    }

    // Overriding hashCode method to maintain consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(name, type); // more robust hashCode calculation using Objects.hash()
    }
}
