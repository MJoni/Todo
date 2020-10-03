package com.qa.Todo.controller;

import com.qa.Todo.dto.TaskDTO;
import com.qa.Todo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("tasks")
public class TaskController {
    private TaskService service;
    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }
    //create
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO){
        TaskDTO created = this.service.createTask(taskDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    //readAll
    @GetMapping("/read")
    public ResponseEntity<List<TaskDTO>> read(){
        return ResponseEntity.ok(this.service.readAllTasks());
    }
    //readById
    @GetMapping("/read/{taskId}")
    public ResponseEntity<TaskDTO> readById(@PathVariable Long taskId){
        return ResponseEntity.ok(this.service.read(taskId));
    }
    //update
    @PutMapping("/read/{taskId}")
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO taskDTO,@PathVariable Long taskId){
        TaskDTO updated = this.service.update(taskDTO,taskId);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }
    //delete
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<TaskDTO> delete(@PathVariable Long taskId){
        return this.service.delete(taskId)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



