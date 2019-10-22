package ru.smartel.renderferma.common;

import java.util.List;

/**
 * Task service
 */
public interface TaskService {
    List<Task> getUserTasks(String userEmail);
    void saveAndHandle(String userEmail) throws TaskSavingException;
}
