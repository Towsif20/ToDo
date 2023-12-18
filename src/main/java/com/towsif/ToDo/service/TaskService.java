package com.towsif.ToDo.service;

import com.towsif.ToDo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface TaskService
{
    Task createTask(Task task);

    Page<Task> getAllTasks(Pageable pageable);

    Task getTaskById(Long id);

    Page<Task> getTasksByDescription(String substring, Pageable pageable);

    String deleteTaskById(Long id);

    Task updateTaskById(Task taskFromRequest, Long id);
}
