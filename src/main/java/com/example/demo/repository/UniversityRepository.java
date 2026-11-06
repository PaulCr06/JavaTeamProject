package com.example.demo.repository;

import com.example.demo.model.University;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityRepository extends InMemoryRepository<University, String>{
}
