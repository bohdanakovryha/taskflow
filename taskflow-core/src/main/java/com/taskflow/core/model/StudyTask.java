package com.taskflow.core.model;

/**
 * A learning task split into chapters: a book, documentation or a course.
 */
public final class StudyTask extends BaseTask {

    private final int totalChapters;
    private int chaptersRead;
    private String source;

    public StudyTask(Long id, String title, Skill skill, Priority priority, int totalChapters) {
        super(id, title, skill, priority);
        this.totalChapters = totalChapters;
    }

    @Override
    public TaskType getType() {
        return TaskType.STUDY;
    }

    public int getTotalChapters() {
        return totalChapters;
    }

    public int getChaptersRead() {
        return chaptersRead;
    }

    public void setChaptersRead(int chaptersRead) {
        this.chaptersRead = chaptersRead;
    }

    /**
     * Returns the learning source, e.g. a book title or a documentation link.
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
