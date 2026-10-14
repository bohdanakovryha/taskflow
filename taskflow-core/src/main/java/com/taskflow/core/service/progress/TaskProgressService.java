package com.taskflow.core.service.progress;

import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.ChecklistItem;
import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.ProjectTask;
import com.taskflow.core.model.StudyTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entry point for progress: picks the right {@link ProgressCalculator} for any task
 * and records progress updates (chapters read, checklist items).
 */
public class TaskProgressService {

    private final ProgressCalculator<StudyTask> studyCalculator;
    private final ProgressCalculator<PracticeTask> practiceCalculator;
    private final ProgressCalculator<ProjectTask> projectCalculator;

    public TaskProgressService() {
        this(new StudyProgressCalculator(), new PracticeProgressCalculator(), new ProjectProgressCalculator());
    }

    public TaskProgressService(ProgressCalculator<StudyTask> studyCalculator,
                               ProgressCalculator<PracticeTask> practiceCalculator,
                               ProgressCalculator<ProjectTask> projectCalculator) {
        this.studyCalculator = Objects.requireNonNull(studyCalculator);
        this.practiceCalculator = Objects.requireNonNull(practiceCalculator);
        this.projectCalculator = Objects.requireNonNull(projectCalculator);
    }

    /**
     * @return completion ratio from 0.0 to 1.0, calculated by the calculator for the task type
     */
    public double progress(BaseTask task) {
        Objects.requireNonNull(task, "task must not be null");
        return switch (task) {
            case StudyTask study -> studyCalculator.progress(study);
            case PracticeTask practice -> practiceCalculator.progress(practice);
            case ProjectTask project -> projectCalculator.progress(project);
        };
    }

    /**
     * @return estimated hours of work left, calculated by the calculator for the task type
     */
    public int estimateHours(BaseTask task) {
        Objects.requireNonNull(task, "task must not be null");
        return switch (task) {
            case StudyTask study -> studyCalculator.estimateHours(study);
            case PracticeTask practice -> practiceCalculator.estimateHours(practice);
            case ProjectTask project -> projectCalculator.estimateHours(project);
        };
    }

    /**
     * Marks one more chapter as read.
     *
     * @throws TaskValidationException if all chapters are already read
     */
    public void readChapter(StudyTask task) {
        Objects.requireNonNull(task, "task must not be null");
        if (task.getChaptersRead() >= task.getTotalChapters()) {
            throw new TaskValidationException(
                    "All " + task.getTotalChapters() + " chapters are already read");
        }
        task.setChaptersRead(task.getChaptersRead() + 1);
    }

    /**
     * Appends a new, not completed item to the checklist.
     *
     * @throws TaskValidationException if the text is blank
     */
    public void addChecklistItem(PracticeTask task, String text) {
        Objects.requireNonNull(task, "task must not be null");
        if (text == null || text.isBlank()) {
            throw new TaskValidationException("Checklist item text must not be blank");
        }
        List<ChecklistItem> checklist = new ArrayList<>(task.getChecklist());
        checklist.add(new ChecklistItem(text.trim(), false));
        task.setChecklist(checklist);
    }

    /**
     * Marks the checklist item at the given index as completed.
     *
     * @throws IndexOutOfBoundsException if there is no item at this index
     */
    public void completeChecklistItem(PracticeTask task, int index) {
        Objects.requireNonNull(task, "task must not be null");
        List<ChecklistItem> checklist = new ArrayList<>(task.getChecklist());
        Objects.checkIndex(index, checklist.size());
        ChecklistItem item = checklist.get(index);
        checklist.set(index, new ChecklistItem(item.text(), true));
        task.setChecklist(checklist);
    }
}
