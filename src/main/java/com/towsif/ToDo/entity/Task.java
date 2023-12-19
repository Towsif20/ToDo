package com.towsif.ToDo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table
public class Task implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 100, message = "Description should be within 100 characters")
    String description;

    Boolean completed;

    public Task()
    {
    }

    public Task(Long id, String description, boolean completed)
    {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(Boolean completed)
    {
        this.completed = completed;
    }
}
