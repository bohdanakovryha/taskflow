package com.taskflow.core.service;

import com.taskflow.core.exception.TaskValidationException;
import com.taskflow.core.model.BaseTask;
import com.taskflow.core.model.Tag;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * Creates normalized tags and manages the tags of a task.
 * Tag names are trimmed and lower-cased, so " Java " and "java" are the same tag.
 */
public class TaskTagService {

    public static final int MAX_TAG_LENGTH = 50;

    /**
     * Creates a tag with a normalized name.
     *
     * @throws TaskValidationException if the name is blank or too long
     */
    public Tag createTag(String name) {
        if (name == null || name.isBlank()) {
            throw new TaskValidationException("Tag name must not be blank");
        }
        String normalized = name.trim().toLowerCase(Locale.ROOT);
        if (normalized.length() > MAX_TAG_LENGTH) {
            throw new TaskValidationException("Tag name must be at most " + MAX_TAG_LENGTH + " characters");
        }
        return new Tag(normalized);
    }

    /**
     * @return {@code true} if the tag was added, {@code false} if the task already had it
     */
    public boolean addTag(BaseTask task, String name) {
        Objects.requireNonNull(task, "task must not be null");
        Set<Tag> tags = new HashSet<>(task.getTags());
        boolean added = tags.add(createTag(name));
        task.setTags(tags);
        return added;
    }

    /**
     * @return {@code true} if the tag was present and has been removed
     */
    public boolean removeTag(BaseTask task, String name) {
        Objects.requireNonNull(task, "task must not be null");
        Set<Tag> tags = new HashSet<>(task.getTags());
        boolean removed = tags.remove(createTag(name));
        task.setTags(tags);
        return removed;
    }

    public boolean hasTag(BaseTask task, String name) {
        Objects.requireNonNull(task, "task must not be null");
        return task.getTags().contains(createTag(name));
    }
}
