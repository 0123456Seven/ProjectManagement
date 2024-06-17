package ru.marsel_bagautdinov.projectmanagerapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marsel_bagautdinov.projectmanagerapp.models.EventStatus;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
    EventStatus findByStatusName(String statusName);
}
