package com.taskflow.core.service;

import com.taskflow.core.TestData;
import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTagServiceTest {

    private final TaskTagService service = new TaskTagService();

    @Test
    void createTag_normalizesName() {
        assertEquals(new Tag("java"), service.createTag("  Java "));
    }

    @Test
    void createTag_blankOrTooLong_throws() {
        assertThrows(TaskValidationException.class, () -> service.createTag("  "));
        assertThrows(TaskValidationException.class, () -> service.createTag(null));
        assertThrows(TaskValidationException.class,
                () -> service.createTag("x".repeat(TaskTagService.MAX_TAG_LENGTH + 1)));
    }

    @Test
    void addTag_ignoresDuplicatesAfterNormalization() {
        PracticeTask task = TestData.practiceTask(1L);

        assertTrue(service.addTag(task, "java"));
        assertFalse(service.addTag(task, " JAVA "));
        assertEquals(1, task.getTags().size());
    }

    @Test
    void removeTag_removesExistingTag() {
        PracticeTask task = TestData.practiceTask(1L);
        service.addTag(task, "java");

        assertTrue(service.removeTag(task, "Java"));
        assertFalse(service.hasTag(task, "java"));
        assertFalse(service.removeTag(task, "java"));
    }
}
