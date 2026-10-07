package com.taskflow.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskFlowAppTest {

    @Test
    void greetingContainsProjectName() {
        assertTrue(TaskFlowApp.greeting().contains("TaskFlow"));
    }
}
