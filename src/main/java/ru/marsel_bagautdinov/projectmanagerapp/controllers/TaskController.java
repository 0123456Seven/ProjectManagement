package ru.marsel_bagautdinov.projectmanagerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.marsel_bagautdinov.projectmanagerapp.models.EventStatus;
import ru.marsel_bagautdinov.projectmanagerapp.models.Task;
import ru.marsel_bagautdinov.projectmanagerapp.service.EventStatusService;
import ru.marsel_bagautdinov.projectmanagerapp.service.ProjectService;
import ru.marsel_bagautdinov.projectmanagerapp.service.TaskService;
import ru.marsel_bagautdinov.projectmanagerapp.service.UserService;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final EventStatusService eventStatusService;
    private final ProjectService projectService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, EventStatusService eventStatusService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.eventStatusService = eventStatusService;
        this.projectService = projectService;
    }

    @GetMapping("/tasks/create")
    public String showCreateTaskForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", eventStatusService.getAllStatuses());
        model.addAttribute("projects", projectService.getAllProjects());
        return "create-task";
    }

    @PostMapping("/tasks/create")
    public String createTask(@RequestParam String description,
                             @RequestParam Long userId,
                             @RequestParam Long status,
                             @RequestParam Long projectId) {
        Task task = new Task();
        task.setDescription(description);
        task.setUser(userService.getUserById(userId));
        task.setEventStatus(eventStatusService.getStatusById(status));
        task.setProject(projectService.getProjectById(projectId));
        taskService.saveTask(task);
        return "redirect:/main";
    }

    @GetMapping("/tasks/details/{taskId}")
    public String getTaskDetails(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            return "error/404"; // Добавьте обработку ошибок, если задача не найдена
        }
        model.addAttribute("task", task);
        model.addAttribute("user", task.getUser());
        model.addAttribute("inProgressTasksCount", taskService.getTasksByStatusAndUser("в работе", task.getUser().getId()).size());
        model.addAttribute("underReviewTasksCount", taskService.getTasksByStatusAndUser("на проверке", task.getUser().getId()).size());
        model.addAttribute("completedTasksCount", taskService.getTasksByStatusAndUser("завершено", task.getUser().getId()).size());
        return "task-details";
    }

    @PostMapping("/tasks/updateStatus")
    public String updateTaskStatus(@RequestParam Long taskId, @RequestParam String status) {
        taskService.updateTaskStatus(taskId, status);
        return "redirect:/main";
    }
}
