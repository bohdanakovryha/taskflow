package com.taskflow.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A hands-on task tracked by a checklist of steps.
 */
public final class PracticeTask extends BaseTask {

    private List<ChecklistItem> checklist;

    public PracticeTask(Long id, String title, Skill skill, Priority priority) {
        super(id, title, skill, priority);
        this.checklist = new ArrayList<>();
    }

    @Override
    public TaskType getType() {
        return TaskType.PRACTICE;
    }

    /**
     * Returns a read-only copy of the checklist.
     */
    public List<ChecklistItem> getChecklist() {
        return List.copyOf(checklist);
    }

    /**
     * Replaces the checklist with a defensive copy of the given list.
     */
    public void setChecklist(List<ChecklistItem> checklist) {
        this.checklist = new ArrayList<>(Objects.requireNonNull(checklist, "checklist must not be null"));
    }
}
