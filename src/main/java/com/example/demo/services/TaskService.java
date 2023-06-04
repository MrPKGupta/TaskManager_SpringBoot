package com.example.demo.services;

import com.example.demo.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskService {
    private final List<Task> tasks;
    private AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task with id " + id + " not found");
        }
    }

    public TaskService() {
        tasks = new ArrayList<>();
        tasks.add(new Task(taskId.getAndIncrement(), "Task1", "Desc1"));
        tasks.add(new Task(taskId.getAndIncrement(), "Task2", "Desc2"));
        tasks.add(new Task(taskId.getAndIncrement(), "Task3", "Desc3"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task createTask(Task task) {
        var newTask = new Task(taskId.incrementAndGet(), task.getTitle(), task.getDescription());
        tasks.add(newTask);
        return newTask;
    }

    public Task getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getTaskId() == id)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(
            int id,
            String title,
            String description
    ) {
        var taskToUpdate = tasks.stream()
                .filter(t -> t.getTaskId() == id)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(title != null) {
            taskToUpdate.setTitle(title);
        }
        if(description != null) {
            taskToUpdate.setDescription(description);
        }
        return taskToUpdate;
    }

    public void deleteTaskById(int id) {
        tasks.removeIf(task -> task.getTaskId() == id);
    }
}
