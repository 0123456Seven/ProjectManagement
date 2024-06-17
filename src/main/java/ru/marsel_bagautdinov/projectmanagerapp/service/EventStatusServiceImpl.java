package ru.marsel_bagautdinov.projectmanagerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marsel_bagautdinov.projectmanagerapp.models.EventStatus;
import ru.marsel_bagautdinov.projectmanagerapp.repo.EventStatusRepository;

import java.util.List;

@Service
public class EventStatusServiceImpl implements EventStatusService {

    private final EventStatusRepository eventStatusRepository;

    @Autowired
    public EventStatusServiceImpl(EventStatusRepository eventStatusRepository) {
        this.eventStatusRepository = eventStatusRepository;
    }

    @Override
    public List<EventStatus> getAllStatuses() {
        return eventStatusRepository.findAll();
    }

    @Override
    public EventStatus getStatusById(Long id) {
        return eventStatusRepository.findById(id).orElse(null);
    }
}
