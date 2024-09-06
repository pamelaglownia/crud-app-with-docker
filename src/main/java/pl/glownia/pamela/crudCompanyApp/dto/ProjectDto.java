package pl.glownia.pamela.crudCompanyApp.dto;

public record ProjectDto(Long id, String name, String description, ManagerDto manager) {
}