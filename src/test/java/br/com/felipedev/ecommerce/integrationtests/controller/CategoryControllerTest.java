package br.com.felipedev.ecommerce.integrationtests.controller;

import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.dto.error.ErrorResponse;
import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserLogin;
import br.com.felipedev.ecommerce.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.felipedev.ecommerce.model.Category;
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
class CategoryControllerTest extends AbstractIntegrationTest {

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

    CategoryRequestDTO categoryRequestDTO;

    static String token;

    static Category categoryEntity;

    @BeforeEach
    void setUp() {
        categoryRequestDTO = new CategoryRequestDTO("ELECTRONICS");
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
    void createCategory_WithValidData_ShouldReturnReturnSavedCategory() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .content(objectMapper.writeValueAsString(categoryRequestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        CategoryResponseDTO categoryResponseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                CategoryResponseDTO.class
        );

        assertNotNull(categoryResponseDTO);
        assertNotNull(categoryResponseDTO.id());
        assertEquals(categoryRequestDTO.description(), categoryResponseDTO.description());
        categoryEntity = new Category(categoryResponseDTO.id(), categoryResponseDTO.description());

    }

    @Order(3)
    @Test
    void createCategory_WhenDescriptionAlreadyExists_ShouldReturnStatus409Conflict() throws Exception {
        String expectedMessage = String.format("The category %s already exists", categoryRequestDTO.description());
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .content(objectMapper.writeValueAsString(categoryRequestDTO))
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
    void findAll_WhenCalled_ShouldReturnCategoryResponseList() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<CategoryResponseDTO> categoryResponseDTOList = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<CategoryResponseDTO>>() {
                }
        );

        CategoryResponseDTO categoryResponseDTO = categoryResponseDTOList.getFirst();
        assertNotNull(categoryResponseDTO);
        assertNotNull(categoryResponseDTO.id());
        assertEquals(categoryEntity.getId(), categoryResponseDTO.id());
        assertEquals(categoryRequestDTO.description(), categoryResponseDTO.description());
    }

    @Order(5)
    @Test
    void updateDescription_WhenIdExistsAndDescriptionIsValid_ShouldReturnUpdatedCategoryResponse() throws Exception {
        Long id = categoryEntity.getId();
        CategoryRequestDTO updatedCategoryDescription = new CategoryRequestDTO("NIKE");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", id)
                .content(objectMapper.writeValueAsString(updatedCategoryDescription))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        CategoryResponseDTO categoryResponseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                CategoryResponseDTO.class
        );

        assertNotNull(categoryResponseDTO);
        assertNotNull(categoryResponseDTO.id());
        assertEquals(id, categoryResponseDTO.id());
        assertEquals(updatedCategoryDescription.description(), categoryResponseDTO.description());
        categoryEntity = new Category(categoryResponseDTO.id(), categoryResponseDTO.description());
    }

    @Order(6)
    @Test
    void updateDescription_WhenIdExistsButDescriptionAlreadyExists_ShouldReturnStatus409Conflict() throws Exception {
        Long id = categoryEntity.getId();
        CategoryRequestDTO updatedCategoryDescription = new CategoryRequestDTO("NIKE");
        String expectedMessage = String.format("The category %s already exists", updatedCategoryDescription.description());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", id)
                .content(objectMapper.writeValueAsString(updatedCategoryDescription))
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
        Long id = categoryEntity.getId() + 100;
        String expectedMessage = String.format("The category with id %d not exists", id);
        CategoryRequestDTO updatedCategoryDescription = new CategoryRequestDTO("ADIDAS");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", id)
                .content(objectMapper.writeValueAsString(updatedCategoryDescription))
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


    @Test
    void deleteById_WhenIdExists_ShouldReturnStatus204NoContent() throws Exception {
        Long id = categoryEntity.getId();
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", id)
                .header("Authorization", token)
        );

        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteById_WhenIdDoesNotExists_ShouldReturnStatus404NotFound() throws Exception {
        Long id = categoryEntity.getId() + 100;
        String expectedMessage = String.format("The category with id %d not exists", id);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", id)
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