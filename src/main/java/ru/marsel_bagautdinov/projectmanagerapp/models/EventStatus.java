package ru.marsel_bagautdinov.projectmanagerapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_status")
@Data
@Getter
@Setter
@NoArgsConstructor
public class EventStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status_name")
    private String statusName;

    // Getters and setters
}
