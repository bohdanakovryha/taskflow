package com.taskflow.core.service;

import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.Status;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Answers deadline-related questions about tasks.
 */
public class DeadlineService {

    /**
     * A task is overdue when its deadline has passed and it is not done yet.
     * A task due today is not overdue.
     */
    public boolean isOverdue(BaseTask task, LocalDate today) {
        Objects.requireNonNull(task, "task must not be null");
        Objects.requireNonNull(today, "today must not be null");
        LocalDate dueDate = task.getDueDate();
        return dueDate != null && dueDate.isBefore(today) && task.getStatus() != Status.DONE;
    }
}
