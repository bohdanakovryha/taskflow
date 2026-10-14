package com.taskflow.core.exception;

/**
 * Base class for all TaskFlow domain exceptions.
 */
public class TaskFlowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TaskFlowException(String message) {
        super(message);
    }

    public TaskFlowException(String message, Throwable cause) {
        super(message, cause);
    }
}
