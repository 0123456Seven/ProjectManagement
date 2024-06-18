package ru.marsel_bagautdinov.projectmanagerapp.service;

import ru.marsel_bagautdinov.projectmanagerapp.models.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getProjectsByStatus(String statusName);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project saveProject(Project project);
    void deleteProject(Long id);
    int getTaskCountByStatus(Long projectId, Long statusId);
    void updateProjectStatus(Long projectId, Long statusId);

}
