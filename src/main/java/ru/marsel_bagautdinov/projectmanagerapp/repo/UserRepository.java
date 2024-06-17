package ru.marsel_bagautdinov.projectmanagerapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marsel_bagautdinov.projectmanagerapp.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
