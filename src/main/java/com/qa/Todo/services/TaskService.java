package com.qa.Todo.services;

import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.exception.TaskNotFoundException;
import com.qa.Todo.presistence.domain.Tasks;
import com.qa.Todo.presistence.repo.TaskRepo;
import com.qa.Todo.utils.TodoBeanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    public TaskRepo repo;
    private ModelMapper mapper;

    @Autowired
    public TaskService(TaskRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }
    private TaskDTO mapToDTO(Tasks tasks){
        return this.mapper.map(tasks, TaskDTO.class);
    }
    private Tasks mapFromDTO(TaskDTO taskDTO){
        return this.mapper.map(taskDTO, Tasks.class);
    }
    //create
    public TaskDTO createTask(TaskDTO taskDTO){
        Tasks toSave = this.mapFromDTO(taskDTO);
        Tasks saved = this.repo.save(toSave);
        return this.mapToDTO(saved);
    }
    //readAll
    public List<TaskDTO> readAllTasks(){
        return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    //readById
    public TaskDTO read(Long taskId){
        return this.mapToDTO(this.repo.findById(taskId).orElseThrow(TaskNotFoundException::new));
    }
    //update
    public TaskDTO update(TaskDTO taskDTO,Long taskId){
        Tasks toUpdate = this.repo.findById(taskId).orElseThrow(TaskNotFoundException::new);
        TodoBeanUtils.mergeNotNull(taskDTO, toUpdate);
        return this.mapToDTO(this.repo.save(toUpdate));

    }
    //delete
    public boolean delete (Long taskId){
        if (!this.repo.existsById(taskId)) {
            throw new TaskNotFoundException();
        }
        this.repo.deleteById(taskId);
        return !this.repo.existsById(taskId);
    }
}
