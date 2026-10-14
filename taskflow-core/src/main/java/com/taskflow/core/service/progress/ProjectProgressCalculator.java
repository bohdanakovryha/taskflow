package com.taskflow.core.service.progress;

import com.taskflow.core.model.ProjectTask;

/**
 * Progress of a project task is derived from its workflow status;
 * remaining effort is based on story points.
 */
public class ProjectProgressCalculator implements ProgressCalculator<ProjectTask> {

    static final int HOURS_PER_STORY_POINT = 3;

    @Override
    public double progress(ProjectTask task) {
        return switch (task.getStatus()) {
            case TODO -> 0.0;
            case IN_PROGRESS -> 0.5;
            case IN_REVIEW -> 0.8;
            case DONE -> 1.0;
        };
    }

    @Override
    public int estimateHours(ProjectTask task) {
        return (int) Math.round(task.getStoryPoints() * HOURS_PER_STORY_POINT * (1.0 - progress(task)));
    }
}
