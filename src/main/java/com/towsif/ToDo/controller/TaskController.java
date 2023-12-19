package com.towsif.ToDo.controller;

import com.towsif.ToDo.service.TaskService;
import com.towsif.ToDo.entity.Task;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody Task task, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();

            throw new ValidationException(errorMessage);
        }

        return taskService.createTask(task);
    }

    @GetMapping
    public Page<Task> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id", name = "sortBy") String sortBy,
                                  @RequestParam(defaultValue = "asc", name = "sortOrder") String sortOrder,
                                  @RequestParam(defaultValue = "", name = "description") String subString)
    {
        return taskService.getAllTasks(page, size, sortBy, sortOrder, subString);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") Long id)
    {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTaskById(@PathVariable("id") Long id)
    {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTaskById(@PathVariable("id") Long id, @RequestBody @Valid Task task, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();

            throw new ValidationException(errorMessage);
        }

        return taskService.updateTaskById(task, id);
    }
}
