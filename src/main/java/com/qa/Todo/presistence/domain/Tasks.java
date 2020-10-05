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


}
