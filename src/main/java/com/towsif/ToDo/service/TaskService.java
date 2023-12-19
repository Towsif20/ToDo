package com.towsif.ToDo.service;

import com.towsif.ToDo.entity.Task;
import org.springframework.data.domain.Page;

public interface TaskService
{
    Task createTask(Task task);

    Page<Task> getAllTasks(int page, int size, String sortBy, String sortOrder, String subString);

    Task getTaskById(Long id);


    String deleteTaskById(Long id);

    Task updateTaskById(Task taskFromRequest, Long id);
}
