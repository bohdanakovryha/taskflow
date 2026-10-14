package com.taskflow.core;

import com.taskflow.core.model.PracticeTask;
import com.taskflow.core.model.Priority;
import com.taskflow.core.model.ProjectTask;
import com.taskflow.core.model.Skill;
import com.taskflow.core.model.StudyTask;

/**
 * Shared test fixtures.
 */
public final class TestData {

    public static final Skill JAVA = new Skill(1L, "Java Core", "Collections, generics, streams");

    private TestData() {
    }

    public static StudyTask studyTask(long id, int totalChapters) {
        return new StudyTask(id, "Read Clean Code", JAVA, Priority.MEDIUM, totalChapters);
    }

    public static PracticeTask practiceTask(long id) {
        return new PracticeTask(id, "Implement repository", JAVA, Priority.HIGH);
    }

    public static ProjectTask projectTask(long id, int storyPoints) {
        return new ProjectTask(id, "Domain model", JAVA, Priority.HIGH, "TF-2", storyPoints);
    }
}
