package com.taskflow.core.model;

import com.taskflow.core.repository.Identifiable;

import java.util.Objects;

/**
 * A skill that groups related tasks, e.g. "Java Core" or "Spring Boot".
 */
public class Skill implements Identifiable<Long> {

    private final Long id;
    private String name;
    private String description;

    public Skill(Long id, String name, String description) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.description = description;
    }

    public Skill(Long id, String name) {
        this(id, name, null);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Skill{id=" + id + ", name='" + name + "'}";
    }
}
