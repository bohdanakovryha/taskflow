package com.taskflow.core.exception;

/**
 * Thrown when a task, skill or tag violates a business rule.
 */
public class TaskValidationException extends TaskFlowException {

    private static final long serialVersionUID = 1L;

    public TaskValidationException(String message) {
        super(message);
    }
}
