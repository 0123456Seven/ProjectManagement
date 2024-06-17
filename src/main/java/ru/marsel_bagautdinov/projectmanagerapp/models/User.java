package ru.marsel_bagautdinov.projectmanagerapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String login;
    private String password;
    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private Role role;

    // Getters and setters
}
