package com.taskflow.core.model;

import com.taskflow.core.TestData;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseTaskTest {

    @Test
    void newTask_hasDefaultValues() {
        PracticeTask task = TestData.practiceTask(1L);

        assertAll(
                () -> assertEquals(Status.TODO, task.getStatus()),
                () -> assertEquals("", task.getDescription()),
                () -> assertTrue(task.getTags().isEmpty()),
                () -> assertNull(task.getDueDate()),
                () -> assertEquals(TestData.JAVA, task.getSkill())
        );
    }

    @Test
    void constructor_nullRequiredField_throws() {
        assertThrows(NullPointerException.class,
                () -> new PracticeTask(1L, null, TestData.JAVA, Priority.LOW));
        assertThrows(NullPointerException.class,
                () -> new PracticeTask(1L, "Title", null, Priority.LOW));
    }

    @Test
    void getTags_returnsReadOnlyCopy() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setTags(Set.of(new Tag("java")));

        Set<Tag> tags = task.getTags();

        assertThrows(UnsupportedOperationException.class, () -> tags.add(new Tag("hack")));
        assertEquals(1, task.getTags().size());
    }

    @Test
    void setTags_storesDefensiveCopy() {
        PracticeTask task = TestData.practiceTask(1L);
        Set<Tag> source = new HashSet<>(Set.of(new Tag("java")));

        task.setTags(source);
        source.add(new Tag("hack"));

        assertEquals(Set.of(new Tag("java")), task.getTags());
    }

    @Test
    void getType_isDefinedBySubclass() {
        assertEquals(TaskType.STUDY, TestData.studyTask(1L, 3).getType());
        assertEquals(TaskType.PRACTICE, TestData.practiceTask(2L).getType());
        assertEquals(TaskType.PROJECT, TestData.projectTask(3L, 2).getType());
    }

    @Test
    void equals_requiresSameTypeAndId() {
        assertEquals(TestData.practiceTask(1L), TestData.practiceTask(1L));
        assertNotEquals(TestData.practiceTask(1L), TestData.practiceTask(2L));
        assertNotEquals(TestData.practiceTask(1L), TestData.studyTask(1L, 3));
    }

    @Test
    void toString_containsTypeIdTitleAndStatus() {
        assertEquals("PracticeTask{id=1, title='Implement repository', status=TODO}",
                TestData.practiceTask(1L).toString());
    }
}
