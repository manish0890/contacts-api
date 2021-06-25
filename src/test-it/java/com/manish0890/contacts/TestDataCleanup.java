package com.manish0890.contacts;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class cleans the test data after each test
public abstract class TestDataCleanup {
    @Autowired
    List<CrudRepository> repositories;

    @AfterEach
    @Transactional
    public void cleanUpRepositories() {
        repositories.forEach(repository -> {
            repository.deleteAll();
            assertEquals(0, repository.count(),
                    "Some documents were not wiped from db after the test");
        });
    }
}
