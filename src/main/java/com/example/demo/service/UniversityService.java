package com.example.demo.service;

import com.example.demo.model.University;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UniversityService {

    private UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }
    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }
    public void deleteUniversity(String Id) {
        universityRepository.delete(Id);
    }
    public List<University> getAllUniversity() {
        return universityRepository.findAll();
    }
    public University getUniversityById(String Id) {
        return universityRepository.findById(Id);
    }
}
