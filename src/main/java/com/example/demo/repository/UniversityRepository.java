package com.example.demo.repository;

import com.example.demo.model.University;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityRepository extends InFileRepository<University, String> {
    public UniversityRepository() {
        super("src/main/resources/data/university.json", University.class);
    }
}
