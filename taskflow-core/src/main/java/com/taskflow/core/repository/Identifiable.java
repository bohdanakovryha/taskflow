package com.taskflow.core.repository;

/**
 * An object that has a unique identifier.
 *
 * @param <ID> identifier type
 */
public interface Identifiable<ID> {

    ID getId();
}
