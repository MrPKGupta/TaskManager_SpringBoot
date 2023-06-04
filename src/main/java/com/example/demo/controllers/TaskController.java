package com.example.demo.controllers;

import com.example.demo.entities.Task;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    TaskController() {
        this.taskService = new TaskService();
    }

    @GetMapping("/tasks")
    List<Task> getTask() {
        return taskService.getTasks();
    }

    /*
     *   Create a new task
     *   @param task @Task
     */
    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/tasks/{id}")
    Task getTaskById(@PathVariable int id) {
        try {
            return taskService.getTaskById(id);
        } catch (TaskService.TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTaskById(@PathVariable int id) {
        taskService.deleteTaskById(id);
    }

    @PatchMapping("/tasks/{id}")
    Task updateTaskById(@PathVariable int id, @RequestBody Task task) {
        return taskService.updateTask(id, task.getTitle(), task.getDescription());
    }
}
