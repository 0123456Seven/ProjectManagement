package ru.marsel_bagautdinov.projectmanagerapp.service;

import ru.marsel_bagautdinov.projectmanagerapp.models.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);
    void updateTaskStatus(Long taskId, String status);
    List<Task> getTasksByStatusAndUser(String status, Long userId);
    List<Task> getTasksByStatus(String status);
    void saveTask(Task task);
    // Другие методы интерфейса
}
