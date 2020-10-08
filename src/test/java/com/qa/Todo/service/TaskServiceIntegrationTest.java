package com.qa.Todo.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.presistence.domain.Tasks;
import com.qa.Todo.presistence.repo.TaskRepo;
import com.qa.Todo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskRepo repo;

    private Tasks testTask;

    private Tasks testTaskWithID;

    private Long id;
    private TaskDTO testTaskDTO;
    private Date startDate = new java.sql.Date(2020-10-11);
    private Date dueDate = new java.sql.Date(2020-11-11);


    @Autowired
    private ModelMapper mapper;

    private TaskDTO mapToDTO(Tasks task) {
        return this.mapper.map(task, TaskDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTask = new Tasks("Your Task", startDate, dueDate ,"You have a mission");

        this.testTaskWithID = this.repo.save(this.testTask);
        this.testTaskDTO = this.mapToDTO(testTaskWithID);
        this.id = this.testTaskWithID.getTask_id();

        // getting around auto-generated id's
//        this.testTaskWithID = this.repo.save(this.testTask);
    }

    @Test
    void testCreateTask() {
//        assertThat(this.mapToDTO(this.testTaskWithID)).isEqualTo(this.service.createTask(mapToDTO(testTask)));
        assertThat(this.testTaskDTO)
                .isEqualTo(this.service.createTask(testTask));
    }

    @Test
    void testFindTaskByID() {
//        assertThat(this.service.read(this.testTaskWithID.getTask_id()))
//                .isEqualTo(this.mapToDTO(this.testTaskWithID));
        assertThat(this.testTaskDTO).isEqualTo(this.service.read(this.id));

    }

    @Test
    void testReadTasks() {
//        assertThat(this.service.readAllTasks())
//                .isEqualTo(Stream.of(this.mapToDTO(testTaskWithID)).collect(Collectors.toList()));
        assertThat(this.service.readAllTasks()).isEqualTo(Stream.of(this.testTaskDTO).collect(Collectors.toList()));

    }

    @Test
    void testUpdateTask() {
        TaskDTO newTask = new TaskDTO(null, "Our Task", startDate, dueDate ,"We have a mission");
        TaskDTO updatedTask = new TaskDTO(this.testTaskWithID.getTask_id(), newTask.getTitle(), newTask.getStart_date(), newTask.getDue_date(),newTask.getBody());

        assertThat(this.service.update(newTask, this.testTaskWithID.getTask_id())).isEqualTo(updatedTask);
    }
    @Test
    void testDeleteTask() {
        assertThat(this.service.delete(this.testTaskWithID.getTask_id())).isTrue();
    }

}