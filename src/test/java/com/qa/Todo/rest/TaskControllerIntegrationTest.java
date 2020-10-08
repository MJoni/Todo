package com.qa.Todo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.presistence.domain.Tasks;

import com.qa.Todo.presistence.repo.TaskRepo;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
//@Sql(scripts = { "classpath:tasks-schema.sql",
//        "classpath:tasks-data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@ActiveProfiles("test")
public class TaskControllerIntegrationTest {
    // autowiring objects for mocking different aspects of the application
    // here, a mock repo (and relevant mappers) are autowired
    // they'll 'just work', so we don't need to worry about them
    // all we're testing is how our controller integrates with the rest of the API

    // mockito's request-making backend
    // you only need this in integration testing - no mocked service required!
    // this acts as postman would, across your whole application
    @Autowired
    private MockMvc mock;

    // i'm reusing my normal repo to ping different things to for testing purposes
    // this is only used for my <expected> objects, not <actual> ones!
    @Autowired
    private TaskRepo repo;

    // this specifically maps POJOs for us, in our case to JSON
    // slightly different from ObjectMapper because we built it ourselves (and use
    // it exclusively on our <expected> objects
    @Autowired
    private ModelMapper modelMapper;

    // this specifically maps objects to JSON format for us
    // slightly different from ModelMapper because this is bundled with mockito
    @Autowired
    private ObjectMapper objectMapper;

    private Tasks testTasks;
    private Tasks testTasksWithId;
    private TaskDTO taskDTO;

    private final String TEST_TITLE = "My Task";

    private final Date TEST_START_DATE = new Date(2020-10-11);
    private final Date TEST_DUE_DATE = new Date(2022-11-11);
    private final String TEST_BODY= "I cannot do it";

    private final String UPDATE_TEST_TITLE= "Your Task";
    private final String UPDATE_TEST_BODY= "You can do it";
    private final Date UPDATE_TEST_START_DATE = new Date(2030-10-11);
    private final Date UPDATE_TEST_DUE_DATE = new Date(2032-11-11);


    private Long id;

    private TaskDTO mapToDTO(Tasks task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();

        this.testTasks = new Tasks(TEST_TITLE, TEST_START_DATE, TEST_DUE_DATE, TEST_BODY);
        this.testTasksWithId = this.repo.save(this.testTasks);
        this.taskDTO = this.mapToDTO(this.testTasksWithId);
        this.id = this.testTasksWithId.getTask_id();
    }

    @Test
    void testCreate() throws Exception {
        this.mock
                .perform(request(HttpMethod.POST, "/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.testTasks))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskDTO)));
    }

    @Test
    void testRead() throws Exception {
        this.mock.perform(request(HttpMethod.GET, "/tasks/read/" + this.id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskDTO)));
    }

    @Test
    void testReadAll() throws Exception {
        List<TaskDTO> taskList = new ArrayList<>();
        taskList.add(this.taskDTO);
        String expected = this.objectMapper.writeValueAsString(taskList);
        String actual = this.mock.perform(request(HttpMethod.GET, "/tasks/read").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    void testUpdate() throws Exception {
        final TaskDTO newTasks = new TaskDTO(this.id,UPDATE_TEST_TITLE, UPDATE_TEST_START_DATE, UPDATE_TEST_DUE_DATE, UPDATE_TEST_BODY);
//        Tasks updatedTasks = new Tasks(newTasks.getTitle(), newTasks.(),
//                newTasks.getType());
//        updatedTasks.setId(this.id);
//        String expected = this.objectMapper.writeValueAsString(this.mapToDTO(updatedTasks));

        String actual = this.mock.perform(request(HttpMethod.PUT, "/tasks/update/" + this.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(newTasks))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()) // 201
                .andReturn().getResponse().getContentAsString();
//        assertEquals(expected, actual);
    }

    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/tasks/delete/" + this.id)).andExpect(status().isNoContent());
    }
}
