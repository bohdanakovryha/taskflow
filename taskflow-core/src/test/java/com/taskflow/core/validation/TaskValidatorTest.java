package com.taskflow.core.validation;

import com.taskflow.core.TestData;
import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.ChecklistItem;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.Priority;
import com.taskflow.core.model.ProjectTask;
import com.taskflow.core.model.Skill;
import com.taskflow.core.model.StudyTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskValidatorTest {

    private final TaskValidator validator = new TaskValidator();

    @Test
    void validate_validTasks_passes() {
        assertAll(
                () -> validator.validate(TestData.studyTask(1L, 3)),
                () -> validator.validate(TestData.practiceTask(2L)),
                () -> validator.validate(TestData.projectTask(3L, 5))
        );
    }

    @Test
    void validate_blankTitle_throws() {
        PracticeTask task = new PracticeTask(1L, "  ", TestData.JAVA, Priority.LOW);

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @Test
    void validate_tooLongTitle_throws() {
        String title = "x".repeat(TaskValidator.MAX_TITLE_LENGTH + 1);
        PracticeTask task = new PracticeTask(1L, title, TestData.JAVA, Priority.LOW);

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @Test
    void validate_blankSkillName_throws() {
        PracticeTask task = new PracticeTask(1L, "Title", new Skill(9L, " "), Priority.LOW);

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @Test
    void validate_studyTaskWithoutChapters_throws() {
        assertThrows(TaskValidationException.class, () -> validator.validate(TestData.studyTask(1L, 0)));
    }

    @Test
    void validate_studyTaskReadMoreThanTotal_throws() {
        StudyTask task = TestData.studyTask(1L, 2);
        task.setChaptersRead(3);

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @Test
    void validate_practiceTaskWithBlankItem_throws() {
        PracticeTask task = TestData.practiceTask(1L);
        task.setChecklist(List.of(new ChecklistItem(" ", false)));

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @ParameterizedTest
    @CsvSource({"tf-1", "TF1", "TF-", "-12", "TF-1a"})
    void validate_invalidJiraKey_throws(String key) {
        ProjectTask task = new ProjectTask(1L, "Setup", TestData.JAVA, Priority.LOW, key, 2);

        assertThrows(TaskValidationException.class, () -> validator.validate(task));
    }

    @ParameterizedTest
    @CsvSource({"0", "-1", "101"})
    void validate_invalidStoryPoints_throws(int points) {
        assertThrows(TaskValidationException.class, () -> validator.validate(TestData.projectTask(1L, points)));
    }
}
