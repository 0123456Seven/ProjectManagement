package ru.marsel_bagautdinov.projectmanagerapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marsel_bagautdinov.projectmanagerapp.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
