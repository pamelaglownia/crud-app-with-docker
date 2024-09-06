package pl.glownia.pamela.crudCompanyApp.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class Manager {
    private String fullName;
    private String email;
    private String phoneNumber;
}