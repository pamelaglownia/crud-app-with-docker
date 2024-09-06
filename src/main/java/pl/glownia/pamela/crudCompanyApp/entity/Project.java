package pl.glownia.pamela.crudCompanyApp.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "project")
    private Team team;

    @Embedded
    private Manager manager;
}