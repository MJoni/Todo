package com.qa.Todo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Todo.dto.UserDTO;
import com.qa.Todo.presistence.domain.Users;
import com.qa.Todo.presistence.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepo repo;
    private ModelMapper modelMapper = new ModelMapper();

    private ObjectMapper objectMapper = new ObjectMapper();

    private Long id;
    private Users testUsers;
    private Users testUserWithId;
    private UserDTO userDTO;

    private final String TEST_FIRST_NAME = "Joni";
    private final String TEST_SURNAME = "Baki";
    private final String TEST_USER_NAME = "mjoni";
    private final String TEST_EMAIL = "mjoni@qa.com";
    private final String TEST_PASS = "123456";

    private final String TEST_UPDATE_FIRST_NAME = "Roni";
    private final String TEST_UPDATE_SURNAME = "Taher";
    private final String TEST_UPDATE_USER_NAME = "rtaher";
    private final String TEST_UPDATE_PASS = "rtaher";


    private UserDTO mapToDTO(Users users) {
        return this.modelMapper.map(users, UserDTO.class);
    }

    @BeforeEach
    void init() {

        this.repo.deleteAll();
        this.testUsers = new Users(TEST_FIRST_NAME, TEST_SURNAME, TEST_USER_NAME, TEST_EMAIL, TEST_PASS);
        this.testUserWithId = this.repo.saveAndFlush(this.testUsers);
        this.id = this.testUserWithId.getUser_id();

        this.userDTO  = new UserDTO(this.id, this.testUsers.getFirst_name(), this.testUsers.getSurname(),this.testUsers.getUser_name(),
                this.testUsers.getEmail(),this.testUsers.getPassword());
    }

    @Test
    void testCreate() throws Exception {
        this.mock
                .perform(request(HttpMethod.POST, "/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.testUsers))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.userDTO)));
    }

    @Test
    void testRead() throws Exception {
        this.mock
                .perform(request(HttpMethod.GET, "/users/read/" + this.id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.userDTO)));
    }

    @Test
    void testReadAll() throws Exception {
        List<Users> usersList = new ArrayList<>();
        usersList.add(this.testUserWithId);

        String content = this.mock
                .perform(request(HttpMethod.GET, "/users/read")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

//        assertEquals(this.objectMapper.writeValueAsString(usersList), content);
    }

    @Test
    void testUpdate() throws Exception {
        UserDTO newUsers = new UserDTO(this.id, TEST_UPDATE_FIRST_NAME,TEST_UPDATE_SURNAME,TEST_UPDATE_USER_NAME,TEST_EMAIL,TEST_UPDATE_PASS);
//        Users updatedUsers = new Users(newUsers.getFirstName(),
//                newUsers.getSurname(), newUsers.getUserName(), newUsers.getEmail(), newUsers.getPassword());
//        updatedUsers.setUserId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/users/update/" + this.id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newUsers)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

//        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedUsers)), result);
    }

    @Test
    void testDelete() throws Exception {
        this.mock
                .perform(request(HttpMethod.DELETE, "/users/delete/" + this.id))
                .andExpect(status().isNoContent());
    }
}
