package br.com.felipedev.ecommerce.integrationtests.controller;

import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserLogin;
import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.ErrorResponse;
import br.com.felipedev.ecommerce.mocks.MockPerson;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import br.com.felipedev.ecommerce.repository.UserRepository;
import br.com.felipedev.ecommerce.service.RoleService;
import br.com.felipedev.ecommerce.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BrandControllerTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    BrandRequestDTO brandRequest;

    BrandResponseDTO brandResponse;
    static String token;

    static Brand brandEntity;

    @BeforeEach
    void setUp() {
        brandRequest = new BrandRequestDTO("ADIDAS");
        if (!userRepository.existsByUsername("admin")){
            // salva um usuário admin no banco de dados
            PersonJuridica personJuridica = MockPerson.mockPersonJuridica();
            personRepository.save(personJuridica);

            User userAdmin;
            userAdmin = new User();
            Role roleAdmin = roleService.getRole(RoleType.ROLE_ADMIN);
            userAdmin.setUsername("admin");
            userAdmin.setPasswordUpdatedAt(LocalDate.now());
            userAdmin.setPassword(passwordEncoder.encode("admin"));
            userAdmin.setRoles(List.of(roleAdmin));
            userAdmin.setPerson(personJuridica);
            userRepository.save(userAdmin);
        }
    }

    @Order(1)
    @Test
    void loginUser_WithValidData_ShouldReturnReturnToken() throws Exception {
        UserLogin userLogin = new UserLogin("admin", "admin");
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .content(objectMapper.writeValueAsString(userLogin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(MockMvcResultMatchers.status().isOk());
        TokenResponseDTO tokenResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                TokenResponseDTO.class
            );
        token = "Bearer " + tokenResponse.token();
    }

    @Order(2)
    @Test
    void createBrand_WithValidData_ShouldReturnReturnSavedBrand() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                .content(objectMapper.writeValueAsString(brandRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        BrandResponseDTO brandResponseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                BrandResponseDTO.class
        );

        assertNotNull(brandResponseDTO);
        assertNotNull(brandResponseDTO.id());
        assertEquals(brandRequest.description(), brandResponseDTO.description());
        brandEntity = new Brand(brandResponseDTO.id(), brandResponseDTO.description());

    }

    @Order(3)
    @Test
    void createBrand_WhenDescriptionAlreadyExists_ShouldReturnStatus409Conflict() throws Exception {
        String expectedMessage = String.format("The brand %s already exists", brandRequest.description());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                .content(objectMapper.writeValueAsString(brandRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());

        ErrorResponse errorResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                ErrorResponse.class
        );

        assertNotNull(errorResponse);
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.error());
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.status());
        assertEquals(expectedMessage, errorResponse.message());

    }

    @Order(4)
    @Test
    void findAll_WhenCalled_ShouldReturnBrandResponseList() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/brands")
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<BrandResponseDTO> brandResponseDTOList = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<BrandResponseDTO>>() {
                }
        );

        BrandResponseDTO brandResponseDTO = brandResponseDTOList.getFirst();
        assertNotNull(brandResponseDTO);
        assertNotNull(brandResponseDTO.id());
        assertEquals(brandEntity.getId(), brandResponseDTO.id());
        assertEquals(brandRequest.description(), brandResponseDTO.description());
    }

    @Order(5)
    @Test
    void updateDescription_WhenIdExistsAndDescriptionIsValid_ShouldReturnUpdatedBrandResponse() throws Exception {
        Long id = brandEntity.getId();
        BrandRequestDTO updatedBrandDescription = new BrandRequestDTO("NIKE");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/brands/{id}", id)
                .content(objectMapper.writeValueAsString(updatedBrandDescription))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        BrandResponseDTO brandResponseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                BrandResponseDTO.class
        );

        assertNotNull(brandResponseDTO);
        assertNotNull(brandResponseDTO.id());
        assertEquals(id, brandResponseDTO.id());
        assertEquals(updatedBrandDescription.description(), brandResponseDTO.description());
        brandEntity = new Brand(brandResponseDTO.id(), brandResponseDTO.description());
    }

    @Order(6)
    @Test
    void updateDescription_WhenIdExistsButDescriptionAlreadyExists_ShouldReturnStatus409Conflict() throws Exception {
        Long id = brandEntity.getId();
        BrandRequestDTO updatedBrandDescription = new BrandRequestDTO("NIKE");
        String expectedMessage = String.format("The brand %s already exists", updatedBrandDescription.description());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/brands/{id}", id)
                .content(objectMapper.writeValueAsString(updatedBrandDescription))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());

        ErrorResponse errorResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                ErrorResponse.class
        );

        assertNotNull(errorResponse);
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.error());
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.status());
        assertEquals(expectedMessage, errorResponse.message());
    }
    @Order(7)
    @Test
    void updateDescription_WhenIdDoesNotExists_ShouldReturnStatus404NotFound() throws Exception {
        Long id = brandEntity.getId() + 100;
        String expectedMessage = String.format("The brand with id %d not exists", id);
        BrandRequestDTO updatedBrandDescription = new BrandRequestDTO("ADIDAS");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/brands/{id}", id)
                .content(objectMapper.writeValueAsString(updatedBrandDescription))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());



        ErrorResponse errorResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                ErrorResponse.class
        );

        assertNotNull(errorResponse);
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorResponse.error());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.status());
        assertEquals(expectedMessage, errorResponse.message());
    }


    @Order(8)
    @Test
    void deleteById_WhenIdExists_ShouldReturnStatus204NoContent() throws Exception {
        Long id = brandEntity.getId();
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/brands/{id}", id)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(9)
    @Test
    void deleteById_WhenIdDoesNotExists_ShouldReturnStatus404NotFound() throws Exception {
        Long id = brandEntity.getId() + 100;
        String expectedMessage = String.format("The brand with id %d not exists", id);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/brands/{id}", id)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        ErrorResponse errorResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                ErrorResponse.class
        );

        assertNotNull(errorResponse);
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorResponse.error());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.status());
        assertEquals(expectedMessage, errorResponse.message());
    }
}