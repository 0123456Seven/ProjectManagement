package ru.marsel_bagautdinov.projectmanagerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.marsel_bagautdinov.projectmanagerapp.models.User;
import ru.marsel_bagautdinov.projectmanagerapp.service.TaskService;
import ru.marsel_bagautdinov.projectmanagerapp.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/user/details/{userId}")
    public String getUserDetails(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "error/404"; // Добавьте обработку ошибок, если пользователь не найден
        }
        model.addAttribute("user", user);
        model.addAttribute("inProgressTasksCount", taskService.getTasksByStatusAndUser("в работе", userId).size());
        model.addAttribute("underReviewTasksCount", taskService.getTasksByStatusAndUser("на проверке", userId).size());
        model.addAttribute("completedTasksCount", taskService.getTasksByStatusAndUser("завершено", userId).size());
        return "user-details";
    }
}
