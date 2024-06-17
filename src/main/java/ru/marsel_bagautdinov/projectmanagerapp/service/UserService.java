package ru.marsel_bagautdinov.projectmanagerapp.service;

import ru.marsel_bagautdinov.projectmanagerapp.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
}
