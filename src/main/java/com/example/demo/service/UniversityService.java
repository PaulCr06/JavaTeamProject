package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Room;
import com.example.demo.model.University;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UniversityService {

    private UniversityRepository universityRepository;
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

    public void deleteUniversity(String id) {
        University u1 = universityRepository.findById(id);

        for (Department d : u1.getDepartments()) {

            departmentService.deleteDepartment(d.getId());
        }

        for (Room r : u1.getRooms()) {

            roomService.deleteRoom(r.getId());
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
