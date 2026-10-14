package com.taskflow.core.model;

import com.taskflow.core.repository.Identifiable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Common state shared by all task types.
 *
 * <p>The class is {@code sealed}: only the three permitted subclasses can extend it.
 * It holds data only; business rules live in the service and validation packages.</p>
 */
public abstract sealed class BaseTask implements Identifiable<Long>
        permits StudyTask, PracticeTask, ProjectTask {

    private final Long id;
    private String title;
    private String description;
    private final Skill skill;
    private Priority priority;
    private LocalDate dueDate;
    private Status status;
    private Set<Tag> tags;
    private final LocalDateTime createdAt;

    protected BaseTask(Long id, String title, Skill skill, Priority priority) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.skill = Objects.requireNonNull(skill, "skill must not be null");
        this.priority = Objects.requireNonNull(priority, "priority must not be null");
        this.description = "";
        this.status = Status.TODO;
        this.tags = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Returns the kind of this task. Each subclass returns its own type.
     */
    public abstract TaskType getType();

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title must not be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Skill getSkill() {
        return skill;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = Objects.requireNonNull(priority, "priority must not be null");
    }

    /**
     * Returns the deadline, or {@code null} if the task has none.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    /**
     * Returns a read-only copy of the tags, so callers cannot modify the task's internal set.
     */
    public Set<Tag> getTags() {
        return Set.copyOf(tags);
    }

    /**
     * Replaces the tags with a defensive copy of the given set.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = new HashSet<>(Objects.requireNonNull(tags, "tags must not be null"));
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Two tasks are equal when they have the same concrete type and the same id.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id.equals(((BaseTask) o).id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "%s{id=%d, title='%s', status=%s}"
                .formatted(getClass().getSimpleName(), id, title, status);
    }
}
