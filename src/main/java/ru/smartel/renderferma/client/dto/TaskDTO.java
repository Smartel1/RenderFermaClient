package ru.smartel.renderferma.client.dto;

import ru.smartel.renderferma.common.Task;
import ru.smartel.renderferma.common.TaskStatus;

public class TaskDTO implements Task {
    private Integer id;
    private TaskStatus status;

    public TaskDTO() {
    }

    public TaskDTO(Integer id, TaskStatus status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
