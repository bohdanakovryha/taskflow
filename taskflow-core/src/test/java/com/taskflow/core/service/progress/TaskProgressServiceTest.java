package com.taskflow.core.service.progress;

import com.taskflow.core.TestData;
import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.ProjectTask;
import com.taskflow.core.model.Status;
import com.taskflow.core.model.StudyTask;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskProgressServiceTest {

    private static final double DELTA = 1e-9;

    private final TaskProgressService service = new TaskProgressService();

    @Test
    void studyTask_progressFollowsChaptersRead() {
        StudyTask task = TestData.studyTask(1L, 4);
        assertEquals(0.0, service.progress(task), DELTA);
        assertEquals(4, service.estimateHours(task));

        service.readChapter(task);

        assertEquals(1, task.getChaptersRead());
        assertEquals(0.25, service.progress(task), DELTA);
        assertEquals(3, service.estimateHours(task));
    }

    @Test
    void readChapter_allRead_throws() {
        StudyTask task = TestData.studyTask(1L, 1);
        service.readChapter(task);

        assertEquals(1.0, service.progress(task), DELTA);
        assertThrows(TaskValidationException.class, () -> service.readChapter(task));
    }

    @Test
    void practiceTask_emptyChecklist_zeroProgress() {
        PracticeTask task = TestData.practiceTask(1L);

        assertEquals(0.0, service.progress(task), DELTA);
        assertEquals(0, service.estimateHours(task));
    }

    @Test
    void practiceTask_progressFollowsCompletedItems() {
        PracticeTask task = TestData.practiceTask(1L);
        service.addChecklistItem(task, "Write interface");
        service.addChecklistItem(task, "Write implementation");
        service.addChecklistItem(task, "Write tests");
        service.addChecklistItem(task, "Open PR");

        service.completeChecklistItem(task, 0);

        assertEquals(0.25, service.progress(task), DELTA);
        assertEquals(3 * PracticeProgressCalculator.HOURS_PER_ITEM, service.estimateHours(task));
        assertTrue(task.getChecklist().get(0).done());
    }

    @Test
    void addChecklistItem_blankText_throws() {
        PracticeTask task = TestData.practiceTask(1L);

        assertThrows(TaskValidationException.class, () -> service.addChecklistItem(task, " "));
    }

    @Test
    void completeChecklistItem_invalidIndex_throws() {
        PracticeTask task = TestData.practiceTask(1L);
        service.addChecklistItem(task, "Only item");

        assertThrows(IndexOutOfBoundsException.class, () -> service.completeChecklistItem(task, 1));
    }

    @Test
    void projectTask_progressFollowsStatus() {
        ProjectTask task = TestData.projectTask(1L, 5);   // 5 SP * 3 h = 15 h

        assertEquals(0.0, service.progress(task), DELTA);
        assertEquals(15, service.estimateHours(task));

        task.setStatus(Status.IN_PROGRESS);
        assertEquals(0.5, service.progress(task), DELTA);
        assertEquals(8, service.estimateHours(task));     // 7.5 rounds to 8

        task.setStatus(Status.IN_REVIEW);
        assertEquals(0.8, service.progress(task), DELTA);
        assertEquals(3, service.estimateHours(task));

        task.setStatus(Status.DONE);
        assertEquals(1.0, service.progress(task), DELTA);
        assertEquals(0, service.estimateHours(task));
    }

    /**
     * Different task types are processed through the common BaseTask type.
     */
    @Test
    void estimateHours_mixedTaskTypes_summedThroughBaseType() {
        StudyTask study = TestData.studyTask(1L, 4);          // 4 h
        PracticeTask practice = TestData.practiceTask(2L);
        service.addChecklistItem(practice, "Step 1");
        service.addChecklistItem(practice, "Step 2");         // 2 * 2 = 4 h
        ProjectTask project = TestData.projectTask(3L, 2);    // 2 * 3 = 6 h

        List<BaseTask> tasks = List.of(study, practice, project);

        int total = tasks.stream().mapToInt(service::estimateHours).sum();

        assertEquals(14, total);
    }
}
