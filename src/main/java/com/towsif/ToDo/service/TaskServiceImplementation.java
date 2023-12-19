package com.towsif.ToDo.service;

import com.towsif.ToDo.entity.Task;
import com.towsif.ToDo.exception.TaskNotFoundException;
import com.towsif.ToDo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImplementation implements TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskPaginationService taskPaginationService;

    @Override
    public Task createTask(Task task)
    {
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAllTasks(int page, int size, String sortBy, String sortOrder, String subString)
    {
        Pageable pageable = taskPaginationService.configurePaginationAndSorting(page, size, sortBy, sortOrder);

        if(subString.isEmpty())
            return taskRepository.findAll(pageable);

        return taskRepository.findByDescriptionContainingIgnoreCase(subString, pageable);
    }

    public Task getTaskById(Long id)
    {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty())
            throw new TaskNotFoundException("Task not found with id " + id);

        return taskOptional.get();
    }

    @Override
    public String deleteTaskById(Long id)
    {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty())
            return "Task not found with id " + id;

        taskRepository.delete(taskOptional.get());

        return "deleted task with id " + id;
    }

    @Override
    public Task updateTaskById(Task taskFromRequest, Long id)
    {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty())
            throw new TaskNotFoundException("Task not found with id " + id);

        Task taskFromDB = taskOptional.get();

        if (taskFromRequest.getDescription() != null && !taskFromRequest.getDescription().isEmpty())
        {
            taskFromDB.setDescription(taskFromRequest.getDescription());
        }

        if(taskFromRequest.isCompleted() != null)
        {
            taskFromDB.setCompleted(taskFromRequest.isCompleted());
        }

        return taskRepository.save(taskFromDB);
    }
}
