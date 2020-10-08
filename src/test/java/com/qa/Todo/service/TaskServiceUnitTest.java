package com.qa.Todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.presistence.domain.Tasks;
import com.qa.Todo.presistence.repo.TaskRepo;
import com.qa.Todo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskServiceUnitTest {

    @Autowired
    private TaskService service;

    @MockBean
    private TaskRepo repo;

    @MockBean
    private ModelMapper mapper;

    private List<Tasks> taskList;

    private Tasks testTasks;

    private Tasks testTasksWithID;

    private TaskDTO taskDTO;

    final Long id = 1L;
    private final String TEST_TITLE = "My Task";

    private final Date TEST_START_DATE = new Date(2020-10-11);
    private final Date TEST_DUE_DATE = new Date(2020-11-11);
    private final String TEST_BODY= "I cannot do it";

    private final String UPDATE_TEST_TITLE= "Your Task";
    private final String UPDATE_TEST_BODY= "You can do it";

//    private TaskDTO mapToDTO(Tasks task) {
//        return this.mapper.map(task, TaskDTO.class);
//    }

    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
        this.taskList.add(testTasks);
        this.testTasks = new Tasks(TEST_TITLE, TEST_START_DATE, TEST_DUE_DATE,TEST_BODY);
        this.testTasksWithID = new Tasks(testTasks.getTitle(), testTasks.getStartDate(), testTasks.getDueDate(), testTasks.getBody());
        this.testTasksWithID.setTask_id(id);
        this.taskDTO =this.mapper.map(testTasksWithID, TaskDTO.class);
    }

    @Test
    void createTasksTest() {
        when(this.repo.save(this.testTasks)).thenReturn(this.testTasksWithID);
        when(this.mapper.map(testTasksWithID, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.taskDTO).isEqualTo(this.service.createTask(this.testTasks));

        verify(this.repo, times(1)).save(this.testTasks);
    }

    @Test
    void deleteTasksTest() {
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        verify(this.repo, times(1)).deleteById(id);
        verify(this.repo, times(2)).existsById(id);
    }

    @Test
    void findTasksByIDTest() {
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTasksWithID));
        when(this.mapper.map(testTasksWithID, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.taskDTO).isEqualTo(this.service.read(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readTaskTest() {

        when(repo.findAll()).thenReturn(this.taskList);
        when(this.mapper.map(testTasksWithID, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.service.readAllTasks().isEmpty()).isFalse();

        verify(repo, times(1)).findAll();
    }

    //TODO: Need to fix this update method
    @Test
    void updateTasksTest() {
        // given
        final Long ID = 1L;
        TaskDTO newTasks = new TaskDTO(null, TEST_TITLE,TEST_START_DATE, TEST_DUE_DATE, TEST_BODY);
        Tasks task = new Tasks(UPDATE_TEST_TITLE, TEST_START_DATE, TEST_DUE_DATE, UPDATE_TEST_BODY);
        task.setTask_id(ID);
        Tasks updatedTasks = new Tasks(newTasks.getTitle(), newTasks.getStart_date(), newTasks.getDue_date(), newTasks.getBody());
        updatedTasks.setTask_id(ID);
        TaskDTO updatedDTO = new TaskDTO(ID, updatedTasks.getTitle(), updatedTasks.getStartDate(), updatedTasks.getDueDate(),updatedTasks.getBody());

        when(this.repo.findById(this.id)).thenReturn(Optional.of(task));
        // You NEED to configure a .equals() method in Tasks.java for this to work
        when(this.repo.save(updatedTasks)).thenReturn(updatedTasks);
        when(this.mapper.map(updatedTasks, TaskDTO.class)).thenReturn(updatedDTO);

        assertThat(updatedDTO).isEqualTo(this.service.update(newTasks, this.id));

        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedTasks);
    }
}
