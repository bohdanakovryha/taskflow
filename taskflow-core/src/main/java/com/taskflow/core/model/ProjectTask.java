package com.taskflow.core.model;

import java.util.Objects;

/**
 * A development task linked to a Jira ticket and estimated in story points.
 */
public final class ProjectTask extends BaseTask {

    private final String jiraKey;
    private final int storyPoints;

    public ProjectTask(Long id, String title, Skill skill, Priority priority, String jiraKey, int storyPoints) {
        super(id, title, skill, priority);
        this.jiraKey = Objects.requireNonNull(jiraKey, "jiraKey must not be null");
        this.storyPoints = storyPoints;
    }

    @Override
    public TaskType getType() {
        return TaskType.PROJECT;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public int getStoryPoints() {
        return storyPoints;
    }
}
