package com.taskflow.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SkillTest {

    @Test
    void equals_basedOnIdOnly() {
        assertEquals(new Skill(1L, "Java"), new Skill(1L, "Java Core"));
        assertNotEquals(new Skill(1L, "Java"), new Skill(2L, "Java"));
    }

    @Test
    void constructor_nullId_throws() {
        assertThrows(NullPointerException.class, () -> new Skill(null, "Java"));
    }
}
