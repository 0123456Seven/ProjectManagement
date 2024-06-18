package ru.marsel_bagautdinov.projectmanagerapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project_info")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "project_status_id", referencedColumnName = "id")
    private EventStatus eventStatus;

    @Column(name = "expected_count_tasks")
    private Long expectedCountTasks;
}
