package ru.marsel_bagautdinov.projectmanagerapp.service;

import ru.marsel_bagautdinov.projectmanagerapp.models.EventStatus;

import java.util.List;

public interface EventStatusService {
    List<EventStatus> getAllStatuses();
    EventStatus getStatusById(Long id);
}
