package com.qa.Todo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskDTO {
    private Long taskId;
    private String title;
    private String body;
    private Date startDate;
    private Date dueDate;
    private List<UserDTO> users = new ArrayList<>();
}
