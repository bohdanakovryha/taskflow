package com.taskflow.core.service.progress;

import com.taskflow.core.model.StudyTask;

/**
 * Progress of a study task is the share of chapters read.
 */
public class StudyProgressCalculator implements ProgressCalculator<StudyTask> {

    static final int HOURS_PER_CHAPTER = 1;

    @Override
    public double progress(StudyTask task) {
        return (double) task.getChaptersRead() / task.getTotalChapters();
    }

    @Override
    public int estimateHours(StudyTask task) {
        return (task.getTotalChapters() - task.getChaptersRead()) * HOURS_PER_CHAPTER;
    }
}
