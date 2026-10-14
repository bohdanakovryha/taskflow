package com.taskflow.core.exception;

import com.taskflow.core.model.Status;

/**
 * Thrown when a task is moved to a status that is not allowed from its current status.
 */
public class InvalidTaskStateException extends TaskFlowException {

    private static final long serialVersionUID = 1L;

    private final Status from;
    private final Status to;

    public InvalidTaskStateException(Status from, Status to) {
        super("Cannot move task from " + from + " to " + to);
        this.from = from;
        this.to = to;
    }

    public Status getFrom() {
        return from;
    }

    public Status getTo() {
        return to;
    }
}
