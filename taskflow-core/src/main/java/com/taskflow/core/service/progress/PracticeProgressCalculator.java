package com.taskflow.core.service.progress;

import com.taskflow.core.model.ChecklistItem;
import com.taskflow.core.model.PracticeTask;

import java.util.List;

/**
 * Progress of a practice task is the share of completed checklist items.
 */
public class PracticeProgressCalculator implements ProgressCalculator<PracticeTask> {

    static final int HOURS_PER_ITEM = 2;

    @Override
    public double progress(PracticeTask task) {
        List<ChecklistItem> checklist = task.getChecklist();
        if (checklist.isEmpty()) {
            return 0.0;
        }
        return (double) countDone(checklist) / checklist.size();
    }

    @Override
    public int estimateHours(PracticeTask task) {
        List<ChecklistItem> checklist = task.getChecklist();
        return (int) (checklist.size() - countDone(checklist)) * HOURS_PER_ITEM;
    }

    private long countDone(List<ChecklistItem> checklist) {
        return checklist.stream().filter(ChecklistItem::done).count();
    }
}
