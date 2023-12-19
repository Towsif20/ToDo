package com.towsif.ToDo.service;

import jakarta.validation.ValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TaskPaginationService
{
    public Pageable configurePaginationAndSorting(int page, int size, String sortBy, String sortOrder)
    {
        sortBy = sortBy.toLowerCase();
        sortOrder = sortOrder.toLowerCase();

        validateSortParameters(sortBy, sortOrder);

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);

        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    private void validateSortParameters(String sortBy, String sortOrder)
    {
        if (!Arrays.asList("id", "description", "completed").contains(sortBy))
            throw new ValidationException("Invalid sortBy parameter. Allowed values: id, description, completed");

        if (!Arrays.asList("asc", "desc").contains(sortOrder))
            throw new ValidationException("Invalid sortOrder parameter. Allowed values: asc, desc");
    }
}
