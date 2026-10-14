package com.taskflow.core.model;

/**
 * A single step in a practice task checklist.
 *
 * @param text step description
 * @param done whether the step is completed
 */
public record ChecklistItem(String text, boolean done) {
}
