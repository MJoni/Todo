package com.qa.Todo.rest;

import com.qa.Todo.controller.TaskController;
import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.presistence.domain.Tasks;
import com.qa.Todo.presistence.domain.Users;
import com.qa.Todo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskControllerUnitTest {

    // the thing we're actually testing
    // (this is the real thing we've made)
    @Autowired
    private TaskController controller;

    // the mock thing that we're connecting to
    // so that any requests we receive are always valid
    @MockBean
    private TaskService service;

    // we need the mapper, because it works with the mock service layer
    @Autowired
    private ModelMapper modelMapper;

    // and we need the dto mapping as well, otherwise we can't test
    // our controller methods (which rely on RE<xDTO>)
    private TaskDTO mapToDTO(Tasks tasks) {
        return this.modelMapper.map(tasks, TaskDTO.class);
    }

    private List<Tasks> taskList;
    private Tasks testTasks;
    private Tasks testTaskWithId;
    private TaskDTO taskDTO;

    private final Long taskId = 1L;
    private final String title = "My Task";
    private final String body = "Hello world!";
    //TODO: fix date values properly
    private Date startDate = new Date();
    private Date dueDate = new Date();
    private final Users users = new Users(1L,"Joni","Baki","mjoni",
            "mjoni@qa.com","123456");

    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
//        this.testTasks = new Tasks(body, strings, title);
//        this.testTasksWithId = new Tasks(testTasks.getName(), testTasks.getStrings(),
//                testTasks.getType());
//        this.testTasksWithId.setId(id);
//        this.guitaristList.add(testTasksWithId);
//        this.guitaristDTO = this.mapToDTO(testTasksWithId);

        //this.testTask = new Tasks();
        this.testTaskWithId = new Tasks(taskId,title, body, startDate, dueDate, users);
        this.testTaskWithId.setTaskId(taskId);
        this.taskList.add(testTaskWithId);
        this.taskDTO = this.mapToDTO(testTaskWithId);
    }

    @Test
    void createTest() {
        // set up what the mock is doing
        // when running some method, return some value we've predefined up there ^
        when(this.service.createTask(taskDTO)).thenReturn(this.taskDTO);

        // these are the same thing:
        // JUNIT: assertEquals(expected, actual)
        // MOCKITO: assertThat(expected).isEqualTo(actual);
        // .isEqualTo(what is the method actually returning?)
        // assertThat(what do we want to compare the method to?)
        TaskDTO testCreated = this.taskDTO;
        assertThat(new ResponseEntity<TaskDTO>(testCreated, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(taskDTO));

        // check that the mocked method we ran our assertion on ... actually ran!
        verify(this.service, times(1)).createTask(this.taskDTO);
    }

    @Test
    void readTest() {
        when(this.service.read(this.taskId)).thenReturn(this.taskDTO);

        TaskDTO testReadOne = this.taskDTO;
        assertThat(new ResponseEntity<TaskDTO>(testReadOne, HttpStatus.OK))
                .isEqualTo(this.controller.readById(this.taskId));

        verify(this.service, times(1)).read(this.taskId);
    }

    // controller <- service
    @Test
    void readAllTest() {
        when(this.service.readAllTasks())
                .thenReturn((List<TaskDTO>) this.taskList.stream().map(this::mapToDTO).collect(Collectors.toList()));

        // getBody() = get the list returned from the controller.read() method
        // isEmpty()).isFalse() - check that that list HAS SOMETHING IN IT
        // we can reason that if the list has something in it, it has a guitarist
        assertThat(this.controller.read().getBody().isEmpty()).isFalse();

        verify(this.service, times(1)).readAllTasks();
    }

    /*
    // controller <- service
    @Test
    void updateTest() {
        // we need to feed the mocked service some updated data values
        // that way we can test if our 6-string guitarist changes to a 4-string
        // 'guitarist'
        TaskDTO newTasks = new TaskDTO(null, "Peter Peter Hughes", 4, "Fender American");
        TaskDTO newTasksWithId = new TaskDTO(this.taskId, newTasks.getName(), newTasks.getStrings(),
                newTasks.getType());

        // feed the mock service the values we made up here ^
        when(this.service.update(newTasks, this.taskId)).thenReturn(newTasksWithId);

        assertThat(new ResponseEntity<TaskDTO>(newTasksWithId, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(newTasks,this.taskId));

        verify(this.service, times(1)).update(newTasks, this.taskId);
    }
    */
    // controller -> service
    @Test
    void deleteTest() {
        this.controller.delete(taskId); // this will ping the service, which is mocked!

        // if the delete function ran, then it pinged the service successfully
        // since our service is a mocked one, we don't need to test anything in it
        // therefore: check if the controller delete function runs
        // if it does, then the test passes
        verify(this.service, times(1)).delete(taskId);
    }
}
