package pl.glownia.pamela.crudCompanyApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.glownia.pamela.crudCompanyApp.dto.CompanyDto;
import pl.glownia.pamela.crudCompanyApp.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);
}
