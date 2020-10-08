package com.qa.Todo.dto;


import lombok.*;

import java.sql.Date;
import java.util.Objects;

@SuppressWarnings("ALL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode


public class TaskDTO {
    private Long task_id;
    private String title;
    private String body;
    private Date start_date;
    private Date due_date;

    public TaskDTO(Long task_id, String title, Date start_date, Date due_date, String body) {
        this.task_id = task_id;
        this.title = title;
        this.body = body;
        this.start_date = start_date;
        this.due_date = due_date;
    }
    public TaskDTO(String title, Date start_date, Date due_date, String body) {
        this.title = title;
        this.body = body;
        this.start_date = start_date;
        this.due_date = due_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDTO taskDTO = (TaskDTO) o;
        return task_id.equals(taskDTO.task_id) &&
                title.equals(taskDTO.title) &&
                body.equals(taskDTO.body) &&
                start_date.equals(taskDTO.start_date) &&
                due_date.equals(taskDTO.due_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task_id, title, body, start_date, due_date);
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

}
