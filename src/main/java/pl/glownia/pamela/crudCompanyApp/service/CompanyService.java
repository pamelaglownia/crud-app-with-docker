package pl.glownia.pamela.crudCompanyApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.glownia.pamela.crudCompanyApp.dto.CompanyDto;
import pl.glownia.pamela.crudCompanyApp.entity.Company;
import pl.glownia.pamela.crudCompanyApp.mapper.CompanyMapper;
import pl.glownia.pamela.crudCompanyApp.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CompanyDto> getCompanyById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDto);
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(companyRepository.save(company));
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company.setId(id);
        return companyMapper.toDto(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}