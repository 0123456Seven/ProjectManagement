package ru.marsel_bagautdinov.projectmanagerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.marsel_bagautdinov.projectmanagerapp.models.Project;
import ru.marsel_bagautdinov.projectmanagerapp.service.EventStatusService;
import ru.marsel_bagautdinov.projectmanagerapp.service.ProjectService;
import ru.marsel_bagautdinov.projectmanagerapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final EventStatusService eventStatusService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, EventStatusService eventStatusService) {
        this.projectService = projectService;
        this.userService = userService;
        this.eventStatusService = eventStatusService;
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model) {
        model.addAttribute("user", userService.getUserById(1L));
        List<Project> inProgressProjects = projectService.getProjectsByStatus("в работе");
        List<Project> completedProjects = projectService.getProjectsByStatus("завершено");

        model.addAttribute("inProgressProjects", inProgressProjects.stream()
                .map(project -> new ProjectDTO(project, projectService.getTaskCountByStatus(project.getId(), 3L)))
                .collect(Collectors.toList()));

        model.addAttribute("completedProjects", completedProjects.stream()
                .map(project -> new ProjectDTO(project, projectService.getTaskCountByStatus(project.getId(), 3L)))
                .collect(Collectors.toList()));

        return "projects";
    }

    @GetMapping("/projects/create")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("statuses", eventStatusService.getAllStatuses().stream()
                .filter(status -> "в работе".equals(status.getStatusName()) || "завершено".equals(status.getStatusName()))
                .collect(Collectors.toList()));
        return "create-project";
    }

    @PostMapping("/projects/create")
    public String createProject(@RequestParam String projectName,
                                @RequestParam Long statusId,
                                @RequestParam Long expectedCountTasks) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setEventStatus(eventStatusService.getStatusById(statusId));
        project.setExpectedCountTasks(expectedCountTasks);
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/details/{projectId}")
    public String showProjectDetails(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("inProgressTasksCount", projectService.getTaskCountByStatus(projectId, 1L)); // ID статуса "в работе"
        model.addAttribute("underReviewTasksCount", projectService.getTaskCountByStatus(projectId, 2L)); // ID статуса "в работе"
        model.addAttribute("completedTasksCount", projectService.getTaskCountByStatus(projectId, 3L)); // ID статуса "завершено"
        return "project-details";
    }

    @PostMapping("/projects/details/{projectId}/updateStatus")
    public String updateProjectStatus(@PathVariable Long projectId, @RequestParam Long statusId) {
        projectService.updateProjectStatus(projectId, statusId);
        return "redirect:/projects/details/" + projectId;
    }

    @PostMapping("/projects/details/{projectId}/delete")
    public String deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return "redirect:/projects";
    }


    public static class ProjectDTO {
        private Project project;
        private int completedTasksCount;

        public ProjectDTO(Project project, int completedTasksCount) {
            this.project = project;
            this.completedTasksCount = completedTasksCount;
        }

        public Project getProject() {
            return project;
        }

        public int getCompletedTasksCount() {
            return completedTasksCount;
        }

        public Long getExpectedCountTasks() {
            return project.getExpectedCountTasks();
        }

        public Long getId() {
            return project.getId();
        }

        public String getProjectName() {
            return project.getProjectName();
        }
    }
}
