package ru.marsel_bagautdinov.projectmanagerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.marsel_bagautdinov.projectmanagerapp.models.Project;
import ru.marsel_bagautdinov.projectmanagerapp.models.Task;
import ru.marsel_bagautdinov.projectmanagerapp.service.ProjectService;
import ru.marsel_bagautdinov.projectmanagerapp.service.TaskService;
import ru.marsel_bagautdinov.projectmanagerapp.service.UserService;

import java.util.List;

@Controller
public class MainController {
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public MainController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/main")
    public String getMain(@RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        List<Project> projects = projectService.getAllProjects();
        Project selectedProject = projectId != null ? projectService.getProjectById(projectId) : null;
        model.addAttribute("user", userService.getUserById(1L));

        List<Task> inProgressTasks = projectId != null ? taskService.getTasksByStatusAndProject("в работе", projectId) : taskService.getTasksByStatus("в работе");
        List<Task> underReviewTasks = projectId != null ? taskService.getTasksByStatusAndProject("на проверке", projectId) : taskService.getTasksByStatus("на проверке");
        List<Task> completedTasks = projectId != null ? taskService.getTasksByStatusAndProject("завершено", projectId) : taskService.getTasksByStatus("завершено");

        model.addAttribute("projects", projects);
        model.addAttribute("selectedProjectId", projectId);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("underReviewTasks", underReviewTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("selectedProject", selectedProject);

        if (selectedProject != null) {
            double completionPercentage = selectedProject.getExpectedCountTasks() > 0 ? ((double) completedTasks.size() / selectedProject.getExpectedCountTasks()) * 100 : 0;
            model.addAttribute("completionPercentage", completionPercentage);
        } else {
            model.addAttribute("completionPercentage", 0);
        }

        return "main";
    }
}
