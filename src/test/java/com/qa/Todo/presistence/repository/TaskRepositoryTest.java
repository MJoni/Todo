package com.qa.Todo.presistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
public class TaskRepositoryTest {


//
//        // you'll only need to test your repo if you've written custom methods
//
//        @Autowired
//        private TaskRepositoryTest repo;
//
//        private final String TEST_NAME = "The Mountain Goats";
//        private final Integer TEST_STRINGS = 6;
//        private final String TEST_TYPE = "Fender Talman";
//
//        private final Tasks TEST_GUITARIST = new Tasks(TEST_NAME, TEST_STRINGS, TEST_TYPE);
//
//        private List<Tasks> results;
//
//        @BeforeEach
//        void init() {
//            this.repo.deleteAll();
//            this.results = new ArrayList<>();
//        }
//
//        @Test
//        void testFindByNameJPQL() throws Exception {
//            this.results.add(TEST_GUITARIST);
//            assertThat(this.repo.findByNameJPQL(TEST_GUITARIST.getName())).isEqualTo(results);
//        }
//
//        @Test
//        void testFindByStringsJPQL() throws Exception {
//            this.results.add(TEST_GUITARIST);
//            assertThat(this.repo.findByStringsJPQL(TEST_GUITARIST.getStrings())).isEqualTo(results);
//        }
//
//        @Test
//        public void testFindByTypeJPQL() throws Exception {
//            this.results.add(TEST_GUITARIST);
//            assertThat(this.repo.findByTypeJPQL(TEST_GUITARIST.getType())).isEqualTo(results);
//        }
    }