package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepository extends InFileRepository<Enrollment, String> {
    public EnrollmentRepository() {
        super("src/main/resources/data/enrollment.json", Enrollment.class);
    }
}
