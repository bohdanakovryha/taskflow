package com.taskflow.core.service;

import com.taskflow.core.TestData;
import com.taskflow.core.exception.InvalidTaskStateException;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskStatusServiceTest {

    private final TaskStatusService service = new TaskStatusService();

    @ParameterizedTest(name = "{0} -> {1}: {2}")
    @CsvSource({
            "TODO,        IN_PROGRESS, true",
            "TODO,        IN_REVIEW,   false",
            "TODO,        DONE,        false",
            "TODO,        TODO,        false",
            "IN_PROGRESS, IN_REVIEW,   true",
            "IN_PROGRESS, TODO,        true",
            "IN_PROGRESS, DONE,        false",
            "IN_REVIEW,   DONE,        true",
            "IN_REVIEW,   IN_PROGRESS, true",
            "IN_REVIEW,   TODO,        false",
            "DONE,        TODO,        false",
            "DONE,        IN_PROGRESS, false",
            "DONE,        IN_REVIEW,   false"
    })
    void canMove_followsWorkflowRules(Status from, Status to, boolean expected) {
        assertEquals(expected, service.canMove(from, to));
    }

    @Test
    void changeStatus_allowedPath_reachesDone() {
        PracticeTask task = TestData.practiceTask(1L);

        service.changeStatus(task, Status.IN_PROGRESS);
        service.changeStatus(task, Status.IN_REVIEW);
        service.changeStatus(task, Status.DONE);

        assertEquals(Status.DONE, task.getStatus());
    }

    @Test
    void changeStatus_forbiddenTransition_throwsAndKeepsStatus() {
        PracticeTask task = TestData.practiceTask(1L);

        InvalidTaskStateException ex = assertThrows(InvalidTaskStateException.class,
                () -> service.changeStatus(task, Status.DONE));

        assertAll(
                () -> assertEquals(Status.TODO, ex.getFrom()),
                () -> assertEquals(Status.DONE, ex.getTo()),
                () -> assertEquals("Cannot move task from TODO to DONE", ex.getMessage()),
                () -> assertEquals(Status.TODO, task.getStatus())
        );
    }

    @Test
    void revertStatus_bypassesWorkflow() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setStatus(Status.DONE);

        service.revertStatus(task, Status.IN_REVIEW);

        assertEquals(Status.IN_REVIEW, task.getStatus());
    }
}
