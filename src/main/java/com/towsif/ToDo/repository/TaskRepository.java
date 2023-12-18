package com.towsif.ToDo.repository;

import com.towsif.ToDo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>
{
    Page<Task> findByDescriptionContainingIgnoreCase(String substring, Pageable pageable);
}
