package com.taskflow.core.service.progress;

import com.taskflow.core.model.BaseTask;

/**
 * Calculates progress and remaining effort for one task type.
 * Each task type has its own implementation.
 *
 * @param <T> the task type this calculator supports
 */
public interface ProgressCalculator<T extends BaseTask> {

    /**
     * @return completion ratio from 0.0 (not started) to 1.0 (finished)
     */
    double progress(T task);

    /**
     * @return estimated hours of work left
     */
    int estimateHours(T task);
}
