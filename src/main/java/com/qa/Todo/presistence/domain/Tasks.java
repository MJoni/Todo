package com.qa.Todo.presistence.domain;
import javax.persistence.*;
import java.util.Date;
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    private Date startDate;
    @Column
    private Date dueDate;

    @ManyToOne
    private Users users;

    @Override
    public String toString() {
        return "Tasks{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                '}';
    }




    public Tasks(Long taskId, String title, String body, Date startDate, Date dueDate, Users users) {
        this.taskId = taskId;
        this.title = title;
        this.body = body;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.users = users;
    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
