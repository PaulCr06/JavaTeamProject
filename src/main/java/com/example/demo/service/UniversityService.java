package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Room;
import com.example.demo.model.University;
import com.example.demo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final DepartmentService departmentService;
    private final RoomService roomService;

    public UniversityService(UniversityRepository universityRepository, DepartmentService departmentService, RoomService roomService) {
        this.universityRepository = universityRepository;
        this.departmentService = departmentService;
        this.roomService = roomService;
    }

    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }

    public University updateUniversity(University university) {
        University existing = universityRepository.findById(university.getId());
        if (existing == null) {
            return null;
        }

        university.setDepartments(existing.getDepartments());
        university.setRooms(existing.getRooms());

        return universityRepository.update(university);
    }

    public void deleteUniversity(String id) {
        University u1 = universityRepository.findById(id);
        if (u1 == null) {
            return;
        }

        if (u1.getDepartments() != null) {
            for (Department d : new ArrayList<>(u1.getDepartments())) {
                departmentService.deleteDepartment(d.getId());
            }
        }

        if (u1.getRooms() != null) {
            for (Room r : new ArrayList<>(u1.getRooms())) {
                roomService.deleteRoom(r.getId());
            }
        }

        universityRepository.delete(id);
    }

    public List<University> getAllUniversity() {
        return universityRepository.findAll();
    }

    public University getUniversityById(String id) {
        return universityRepository.findById(id);
    }
}
