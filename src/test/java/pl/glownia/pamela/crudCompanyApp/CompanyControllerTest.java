package pl.glownia.pamela.crudCompanyApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.glownia.pamela.crudCompanyApp.controller.CompanyController;
import pl.glownia.pamela.crudCompanyApp.dto.CompanyDto;
import pl.glownia.pamela.crudCompanyApp.dto.DepartmentDto;
import pl.glownia.pamela.crudCompanyApp.dto.TeamDto;
import pl.glownia.pamela.crudCompanyApp.dto.ProjectDto;
import pl.glownia.pamela.crudCompanyApp.dto.ManagerDto;
import pl.glownia.pamela.crudCompanyApp.service.CompanyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCompanies() throws Exception {
        // Create Managers
        ManagerDto manager1 = new ManagerDto(1L, "Peter Griffin", "peter@example.com", "123-456-789");
        ManagerDto manager2 = new ManagerDto(2L, "SpongeBob SquarePants", "spongebob@example.com", "987-654-321");

        // Create Projects with Managers
        ProjectDto project1 = new ProjectDto(1L, "Stone Age Solutions", "Modern solutions with a prehistoric twist.", manager1);
        ProjectDto project2 = new ProjectDto(2L, "Underwater Tech", "New underwater technologies.", manager2);

        // Create Teams with Projects
        TeamDto team1 = new TeamDto(1L, "Griffin’s Heroes", project1);
        TeamDto team2 = new TeamDto(2L, "SpongeBob Squad", project2);

        // Create Departments with Teams
        DepartmentDto dept1 = new DepartmentDto(1L, "Development", List.of(team1));
        DepartmentDto dept2 = new DepartmentDto(2L, "Marketing", List.of(team2));

        // Create Companies with Departments
        CompanyDto company1 = new CompanyDto(1L, "Create IT", List.of(dept1));
        CompanyDto company2 = new CompanyDto(2L, "You can do IT", List.of(dept2));

        when(companyService.getAllCompanies()).thenReturn(List.of(company1, company2));

        // Test response structure and content
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Create IT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments[0].name").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments[0].teams[0].name").value("Griffin’s Heroes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments[0].teams[0].project.name").value("Stone Age Solutions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments[0].teams[0].project.manager.name").value("Peter Griffin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("You can do IT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments[0].name").value("Marketing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments[0].teams[0].name").value("SpongeBob Squad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments[0].teams[0].project.name").value("Underwater Tech"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments[0].teams[0].project.manager.name").value("SpongeBob SquarePants"));
    }

    @Test
    void testGetCompanyById() throws Exception {
        ManagerDto manager = new ManagerDto(1L, "Fred Flinstone", "fred@example.com", "111-222-333");
        ProjectDto project = new ProjectDto(1L, "Stone Age Solutions", "Modern solutions with a prehistoric twist.", manager);
        TeamDto team = new TeamDto(1L, "Flintstone Force", project);
        DepartmentDto department = new DepartmentDto(1L, "Infrastructure", List.of(team));
        CompanyDto company = new CompanyDto(1L, "Bedrock Inc.", List.of(department));

        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.of(company));

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bedrock Inc."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].name").value("Infrastructure"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].name").value("Flintstone Force"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.name").value("Stone Age Solutions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.manager.name").value("Fred Flinstone"));
    }

    @Test
    void testGetCompanyByIdNotFound() throws Exception {
        when(companyService.getCompanyById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateCompany() throws Exception {
        ManagerDto manager = new ManagerDto(1L, "New Manager", "new.manager@example.com", "555-666-777");
        ProjectDto project = new ProjectDto(1L, "New Project", "A new project description.", manager);
        TeamDto team = new TeamDto(1L, "New Team", project);
        DepartmentDto department = new DepartmentDto(1L, "New Department", List.of(team));
        CompanyDto companyDto = new CompanyDto(null, "New Company", List.of(department));
        CompanyDto createdCompany = new CompanyDto(1L, "New Company", List.of(department));

        when(companyService.createCompany(any(CompanyDto.class))).thenReturn(createdCompany);

        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Company"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].name").value("New Department"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].name").value("New Team"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.name").value("New Project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.manager.name").value("New Manager"));
    }

    @Test
    void testUpdateCompany() throws Exception {
        ManagerDto manager = new ManagerDto(1L, "Updated Manager", "updated.manager@example.com", "888-999-000");
        ProjectDto project = new ProjectDto(1L, "Updated Project", "Updated project description.", manager);
        TeamDto team = new TeamDto(1L, "Updated Team", project);
        DepartmentDto department = new DepartmentDto(1L, "Updated Department", List.of(team));
        CompanyDto companyDto = new CompanyDto(null, "Updated Company", List.of(department));
        CompanyDto updatedCompany = new CompanyDto(1L, "Updated Company", List.of(department));

        when(companyService.updateCompany(anyLong(), any(CompanyDto.class))).thenReturn(updatedCompany);

        mockMvc.perform(MockMvcRequestBuilders.put("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Company"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].name").value("Updated Department"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].name").value("Updated Team"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.name").value("Updated Project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments[0].teams[0].project.manager.name").value("Updated Manager"));
    }

    @Test
    void testDeleteCompany() throws Exception {
        doNothing().when(companyService).deleteCompany(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
