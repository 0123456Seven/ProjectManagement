package ru.marsel_bagautdinov.projectmanagerapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marsel_bagautdinov.projectmanagerapp.models.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEventStatus_StatusName(String status);
    List<Task> findByUserIdAndEventStatus_StatusName(Long userId, String status);

    List<Task> findByEventStatus_StatusNameAndUser_Id(String status, Long userId);
}
