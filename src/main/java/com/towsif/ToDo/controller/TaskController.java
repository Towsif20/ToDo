package com.towsif.ToDo.controller;

import com.towsif.ToDo.service.TaskService;
import com.towsif.ToDo.entity.Task;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
                                  @RequestParam(required = false, name = "sortBy") String sortBy,
                                  @RequestParam(required = false, name = "sortOrder") String sortOrder)
    {
        Sort sort = Sort.unsorted();

        if (sortBy != null && sortOrder == null)
        {
            sortOrder = "asc";
        }

        if(sortBy != null)
        {
            sortBy = sortBy.toLowerCase();
            sortOrder = sortOrder.toLowerCase();

            validateSortParameters(sortBy, sortOrder);

            Sort.Direction direction = Sort.Direction.fromString(sortOrder);

            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") Long id)
    {
        return taskService.getTaskById(id);
    }

    @GetMapping("/description")
    public Page<Task> getTasksByDescription(@RequestParam(name = "description") String substring,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false, name = "sortBy") String sortBy,
                                            @RequestParam(required = false, name = "sortOrder") String sortOrder)
    {
        Sort sort = Sort.unsorted();

        if (sortBy != null && sortOrder == null)
        {
            sortOrder = "asc";
        }

        if(sortBy != null)
        {
            sortBy = sortBy.toLowerCase();
            sortOrder = sortOrder.toLowerCase();

            validateSortParameters(sortBy, sortOrder);

            Sort.Direction direction = Sort.Direction.fromString(sortOrder);

            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return taskService.getTasksByDescription(substring, pageable);
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

    private void validateSortParameters(String sortBy, String sortOrder)
    {
        if (!Arrays.asList("id", "description", "completed").contains(sortBy))
            throw new ValidationException("Invalid sortBy parameter. Allowed values: id, description, completed");

        if (!Arrays.asList("asc", "desc").contains(sortOrder))
            throw new ValidationException("Invalid sortOrder parameter. Allowed values: asc, desc");
    }
}
