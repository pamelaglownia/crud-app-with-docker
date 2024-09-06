package pl.glownia.pamela.crudCompanyApp.dto;

import java.util.List;

public record CompanyDto(Long id, String name, List<DepartmentDto> departments) {
}