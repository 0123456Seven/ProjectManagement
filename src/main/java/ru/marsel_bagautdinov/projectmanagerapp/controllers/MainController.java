package ru.marsel_bagautdinov.projectmanagerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.marsel_bagautdinov.projectmanagerapp.models.User;
import ru.marsel_bagautdinov.projectmanagerapp.service.TaskService;
import ru.marsel_bagautdinov.projectmanagerapp.service.UserService;

import java.util.List;
@Controller
public class MainController {
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public MainController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/main")
    public String getMain(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("inProgressTasks", taskService.getTasksByStatus("в работе"));
        model.addAttribute("underReviewTasks", taskService.getTasksByStatus("на проверке"));
        model.addAttribute("completedTasks", taskService.getTasksByStatus("завершено"));

        return "main";
    }
}
