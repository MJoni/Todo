package com.qa.Todo.presistence.repo;

import com.qa.Todo.presistence.domain.Tasks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepo extends JpaRepository<Tasks, Long> {
}
