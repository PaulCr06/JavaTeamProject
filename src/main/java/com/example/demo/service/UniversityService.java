package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Room;
import com.example.demo.model.University;
import com.example.demo.repository.UniversityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private void validateUniversity(University university) {
        if (university == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: University cannot be null!");
        }
        if (university.getName() == null || university.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: University name must not be empty!");
        }
        if (university.getCity() == null || university.getCity().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: University city must not be empty!");
        }
    }

    public University saveUniversity(University university) {
        validateUniversity(university);
        return universityRepository.save(university);
    }

    public University updateUniversity(University university) {
        if (university == null || university.getId() == null) {
            return null;
        }
        validateUniversity(university);

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

        if (u1.getDepartments() != null && departmentService != null) {
            for (Department d : new ArrayList<>(u1.getDepartments())) {
                departmentService.deleteDepartment(d.getId());
            }
        }

        if (u1.getRooms() != null && roomService != null) {
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
        University university = universityRepository.findById(id);
        if (university != null) {
            if (departmentService != null && university.getDepartments() != null) {
                List<Department> freshDepts = new ArrayList<>();
                for (Department d : university.getDepartments()) {
                    Department loaded = departmentService.getDepartmentById(d.getId());
                    freshDepts.add(loaded != null ? loaded : d);
                }
                university.setDepartments(freshDepts);
            }
            if (roomService != null && university.getRooms() != null) {
                List<Room> freshRooms = new ArrayList<>();
                for (Room r : university.getRooms()) {
                    Room loaded = roomService.getRoom(r.getId());
                    freshRooms.add(loaded != null ? loaded : r);
                }
                university.setRooms(freshRooms);
            }
        }
        return university;
    }
}
