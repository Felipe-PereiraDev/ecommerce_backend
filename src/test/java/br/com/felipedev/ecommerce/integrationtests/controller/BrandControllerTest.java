package br.com.felipedev.ecommerce.integrationtests.controller;

import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.dto.error.ErrorResponse;
import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserLogin;
import br.com.felipedev.ecommerce.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import br.com.felipedev.ecommerce.repository.UserRepository;
import br.com.felipedev.ecommerce.service.RoleService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    }

    @Order(1)
    @Test
    void loginUser_WithValidData_ShouldReturnReturnToken() throws Exception {
        UserLogin userLogin = new UserLogin("admin@email.com", "admin123");
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
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.getError());
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());

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
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.getError());
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());
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
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorResponse.getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());
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
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorResponse.getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());
    }
}