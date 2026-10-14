package com.taskflow.core.service;

import com.taskflow.core.exception.InvalidTaskStateException;
import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.Status;

import java.util.Objects;

/**
 * Owns the task workflow: which status transitions are allowed and how to apply them.
 *
 * <pre>
 * TODO        -> IN_PROGRESS
 * IN_PROGRESS -> IN_REVIEW | TODO
 * IN_REVIEW   -> DONE | IN_PROGRESS
 * DONE        -> (final)
 * </pre>
 */
public class TaskStatusService {

    public boolean canMove(Status from, Status to) {
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        return switch (from) {
            case TODO -> to == Status.IN_PROGRESS;
            case IN_PROGRESS -> to == Status.IN_REVIEW || to == Status.TODO;
            case IN_REVIEW -> to == Status.DONE || to == Status.IN_PROGRESS;
            case DONE -> false;
        };
    }

    /**
     * Moves the task to the next status if the transition is allowed.
     *
     * @throws InvalidTaskStateException if the transition is not allowed
     */
    public void changeStatus(BaseTask task, Status next) {
        Objects.requireNonNull(task, "task must not be null");
        if (!canMove(task.getStatus(), next)) {
            throw new InvalidTaskStateException(task.getStatus(), next);
        }
        task.setStatus(next);
    }

    /**
     * Restores a previous status without checking the workflow.
     * Intended only for undo operations; use {@link #changeStatus} everywhere else.
     */
    public void revertStatus(BaseTask task, Status previous) {
        Objects.requireNonNull(task, "task must not be null");
        task.setStatus(previous);
    }
}
