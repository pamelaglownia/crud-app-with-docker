package pl.glownia.pamela.crudCompanyApp.dto;

import java.util.List;
public record DepartmentDto(Long id, String name, List<TeamDto> teams) {
}