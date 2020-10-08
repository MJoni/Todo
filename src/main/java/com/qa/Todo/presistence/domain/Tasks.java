package com.qa.Todo.presistence.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;


@SuppressWarnings("ALL")
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    private Date start_date;
    @Column
    private Date due_date;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;

    public Tasks(String title, Date start_date, Date due_date, String body) {
        this.title = title;
        this.body = body;
        this.start_date = start_date;
        this.due_date = due_date;

    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskId=" + task_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", startDate=" + start_date +
                ", dueDate=" + due_date +
                '}';
    }

    public Tasks(Long task_id, String title, String body, Date startDate, Date dueDate, Users users) {
        this.task_id = task_id;
        this.title = title;
        this.body = body;
        this.start_date = startDate;
        this.due_date = dueDate;
        this.users = users;
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

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date startDate) {
        this.start_date = startDate;
    }

    public Date getDueDate() {
        return due_date;
    }

    public void setDueDate(Date due_date) {
        this.due_date = due_date;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return task_id.equals(tasks.task_id) &&
                title.equals(tasks.title) &&
                body.equals(tasks.body) &&
                start_date.equals(tasks.start_date) &&
                due_date.equals(tasks.due_date);
//                users.equals(tasks.users);

    }

    @Override
    public int hashCode() {
        return Objects.hash(task_id, title, body, start_date, due_date, users);
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
