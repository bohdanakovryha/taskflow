package com.taskflow.core.service;

import com.taskflow.core.TestData;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeadlineServiceTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 14);

    private final DeadlineService service = new DeadlineService();

    @Test
    void isOverdue_noDueDate_false() {
        assertFalse(service.isOverdue(TestData.practiceTask(1L), TODAY));
    }

    @Test
    void isOverdue_pastDueAndNotDone_true() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setDueDate(TODAY.minusDays(1));

        assertTrue(service.isOverdue(task, TODAY));
    }

    @Test
    void isOverdue_dueToday_false() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setDueDate(TODAY);

        assertFalse(service.isOverdue(task, TODAY));
    }

    @Test
    void isOverdue_pastDueButDone_false() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setDueDate(TODAY.minusDays(1));
        task.setStatus(Status.DONE);

        assertFalse(service.isOverdue(task, TODAY));
    }
}
