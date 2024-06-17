package ru.marsel_bagautdinov.projectmanagerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marsel_bagautdinov.projectmanagerapp.models.Task;
import ru.marsel_bagautdinov.projectmanagerapp.repo.TaskRepository;
import ru.marsel_bagautdinov.projectmanagerapp.repo.EventStatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EventStatusRepository eventStatusRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, EventStatusRepository eventStatusRepository) {
        this.taskRepository = taskRepository;
        this.eventStatusRepository = eventStatusRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void updateTaskStatus(Long taskId, String status) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setEventStatus(eventStatusRepository.findByStatusName(status));
            taskRepository.save(task);
        }
    }

    @Override
    public List<Task> getTasksByStatusAndUser(String status, Long userId) {
        return taskRepository.findByEventStatus_StatusNameAndUser_Id(status, userId);
    }
    @Override
    public void saveTask(Task task) {
        task.setCreateDate(LocalDateTime.now()); // Устанавливаем текущую дату и время
        taskRepository.save(task);
    }
    @Override
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByEventStatus_StatusName(status);
    }
}
