package ru.marsel_bagautdinov.projectmanagerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marsel_bagautdinov.projectmanagerapp.models.EventStatus;
import ru.marsel_bagautdinov.projectmanagerapp.models.Project;
import ru.marsel_bagautdinov.projectmanagerapp.repo.EventStatusRepository;
import ru.marsel_bagautdinov.projectmanagerapp.repo.ProjectRepository;
import ru.marsel_bagautdinov.projectmanagerapp.repo.TaskRepository;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EventStatusRepository eventStatusRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, EventStatusRepository eventStatusRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Project> getProjectsByStatus(String statusName) {
        EventStatus status = eventStatusRepository.findByStatusName(statusName);
        return projectRepository.findByEventStatus(status);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public int getTaskCountByStatus(Long projectId, Long statusId) {
        return taskRepository.countTasksByProjectAndStatus(projectId, statusId);
    }

    @Override
    public void updateProjectStatus(Long projectId, Long statusId) {
        Project project = getProjectById(projectId);
        if (project != null) {
            EventStatus status = eventStatusRepository.findById(statusId).orElse(null);
            if (status != null) {
                project.setEventStatus(status);
                saveProject(project);
            }
        }
    }
}
