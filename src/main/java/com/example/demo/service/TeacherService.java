package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherService(TeacherRepository teacherRepository, TeachingAssignmentRepository teachingAssignmentRepository, DepartmentRepository departmentRepository) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    public Teacher saveTeacher(Teacher teacher) {
        if (departmentRepository.findById(teacher.getDepartmentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        Department department = departmentRepository.findById(teacher.getDepartmentId());
        if (department != null) {
            if (department.getTeachers() == null) {
                department.setTeachers(new ArrayList<>());
            }
            department.getTeachers().add(savedTeacher);
        }

        return savedTeacher;
    }

    public Teacher updateTeacher(Teacher teacher) {
        if (departmentRepository.findById(teacher.getDepartmentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }
        Teacher existing = teacherRepository.findById(teacher.getId());
        if (existing == null) {
            return null;
        }
        return teacherRepository.update(teacher);
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    public void deleteTeacher(String id) {
        for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
            if (id.equals(assignment.getStaffId())) {
                teachingAssignmentRepository.delete(assignment.getId());
            }
        }
        teacherRepository.delete(id);
    }
}
