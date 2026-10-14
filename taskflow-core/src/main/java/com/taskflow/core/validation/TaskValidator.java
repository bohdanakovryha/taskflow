package com.taskflow.core.validation;

import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.ChecklistItem;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.ProjectTask;
import com.taskflow.core.model.Skill;
import com.taskflow.core.model.StudyTask;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Checks business rules for skills and tasks.
 */
public class TaskValidator {

    public static final int MAX_TITLE_LENGTH = 200;
    public static final int MAX_SKILL_NAME_LENGTH = 100;
    public static final int MAX_STORY_POINTS = 100;

    /** Jira key format: uppercase project key, dash, number (e.g. TF-12). */
    private static final Pattern JIRA_KEY = Pattern.compile("[A-Z][A-Z0-9]*-\\d+");

    /**
     * Validates the common fields and the fields specific to the task type.
     *
     * @throws TaskValidationException if any rule is violated
     */
    public void validate(BaseTask task) {
        Objects.requireNonNull(task, "task must not be null");
        validateTitle(task.getTitle());
        validateSkill(task.getSkill());

        switch (task) {
            case StudyTask study -> validateStudyTask(study);
            case PracticeTask practice -> validatePracticeTask(practice);
            case ProjectTask project -> validateProjectTask(project);
        }
    }

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new TaskValidationException("Task title must not be blank");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new TaskValidationException("Task title must be at most " + MAX_TITLE_LENGTH + " characters");
        }
    }

    public void validateSkill(Skill skill) {
        if (skill == null) {
            throw new TaskValidationException("Skill must not be null");
        }
        String name = skill.getName();
        if (name == null || name.isBlank()) {
            throw new TaskValidationException("Skill name must not be blank");
        }
        if (name.length() > MAX_SKILL_NAME_LENGTH) {
            throw new TaskValidationException("Skill name must be at most " + MAX_SKILL_NAME_LENGTH + " characters");
        }
    }

    private void validateStudyTask(StudyTask task) {
        if (task.getTotalChapters() <= 0) {
            throw new TaskValidationException("Total chapters must be positive");
        }
        if (task.getChaptersRead() < 0 || task.getChaptersRead() > task.getTotalChapters()) {
            throw new TaskValidationException(
                    "Chapters read must be between 0 and " + task.getTotalChapters());
        }
    }

    private void validatePracticeTask(PracticeTask task) {
        for (ChecklistItem item : task.getChecklist()) {
            if (item.text() == null || item.text().isBlank()) {
                throw new TaskValidationException("Checklist item text must not be blank");
            }
        }
    }

    private void validateProjectTask(ProjectTask task) {
        if (!JIRA_KEY.matcher(task.getJiraKey()).matches()) {
            throw new TaskValidationException(
                    "Invalid Jira key '" + task.getJiraKey() + "', expected format like TF-12");
        }
        if (task.getStoryPoints() <= 0 || task.getStoryPoints() > MAX_STORY_POINTS) {
            throw new TaskValidationException("Story points must be between 1 and " + MAX_STORY_POINTS);
        }
    }
}
