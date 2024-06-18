package ru.marsel_bagautdinov.projectmanagerapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.marsel_bagautdinov.projectmanagerapp.models.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEventStatus_StatusName(String status);
    List<Task> findByUserIdAndEventStatus_StatusName(Long userId, String status);

    List<Task> findByEventStatus_StatusNameAndUser_Id(String status, Long userId);
    int countByProjectIdAndEventStatusId(Long projectId, Long eventStatusId);
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.id = :projectId AND t.eventStatus.id = :statusId")
    int countTasksByProjectAndStatus(Long projectId, Long statusId);
    @Query("SELECT t FROM Task t WHERE t.eventStatus.statusName = :status AND t.project.id = :projectId")
    List<Task> findByStatusAndProjectId(@Param("status") String status, @Param("projectId") Long projectId);

}
