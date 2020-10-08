package com.qa.Todo.service;

import com.qa.Todo.dto.UserDTO;
import com.qa.Todo.presistence.domain.Users;
import com.qa.Todo.presistence.repo.UserRepo;
import com.qa.Todo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepo repo;

    private Users testUser;

    private Users testUserWithID;

    private Long id;
    private UserDTO testUserDTO;

    private final String TEST_FIRST_NAME = "Joni";
    private final String TEST_SURNAME = "Baki";
    private final String TEST_USER_NAME = "mjoni";
    private final String TEST_USER_EMAIL = "mjoni@qa.com";
    private final String TEST_PASS = "123456";

    private final String TEST_UPDATE_FIRST_NAME = "Roni";
    private final String TEST_UPDATE_SURNAME = "Taher";
    private final String TEST_UPDATE_USER_NAME = "rtaher";
    private final String TEST_UPDATE_PASS = "rtaher";

    @Autowired
    private ModelMapper mapper;

    private UserDTO mapToDTO(Users task) {
        return this.mapper.map(task, UserDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testUser = new Users(TEST_FIRST_NAME, TEST_SURNAME, TEST_USER_NAME,TEST_USER_EMAIL,TEST_PASS);
        this.testUserWithID = this.repo.save(this.testUser);
        this.testUserDTO = this.mapToDTO(testUserWithID);
        this.id = this.testUserWithID.getUser_id();

    }

    @Test
    void testCreateUser() {
        assertThat(this.testUserDTO)
                .isEqualTo(this.service.createUser(testUser));
    }


    @Test
    void testFindTaskByID() {
        assertThat(this.service.read(this.testUserWithID.getUser_id()))
                .isEqualTo(this.mapToDTO(this.testUserWithID));
    }

    @Test
    void testReadUsers() {
        assertThat(this.service.readAllUsers())
                .isEqualTo(Stream.of(this.mapToDTO(testUserWithID)).collect(Collectors.toList()));
    }

    @Test
    void testUpdateUser() {
        UserDTO newTask = new UserDTO(TEST_UPDATE_FIRST_NAME, TEST_UPDATE_SURNAME,TEST_UPDATE_USER_NAME,TEST_USER_EMAIL,TEST_UPDATE_PASS);
        UserDTO updatedTask = new UserDTO(this.testUserWithID.getUser_id(), newTask.getFirst_name(), newTask.getSurname(), newTask.getUser_name(),newTask.getEmail(), newTask.getPassword());

        assertThat(this.service.update(newTask, this.testUserWithID.getUser_id())).isEqualTo(updatedTask);
    }
    @Test
    void testDeleteUser() {
        assertThat(this.service.delete(this.testUserWithID.getUser_id())).isTrue();
    }
}
