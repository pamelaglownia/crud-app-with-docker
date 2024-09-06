package pl.glownia.pamela.crudCompanyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.glownia.pamela.crudCompanyApp.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}