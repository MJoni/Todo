package com.qa.Todo.dto;

import java.util.ArrayList;
import java.util.List;

//converting our POJO to JSON
public class UserDTO {
    private Long userId;
    private String firstName;
    private String surName;
    private String userName;
    private String email;
    private String password;
    private List<TaskDTO> tasks = new ArrayList<>();

}
