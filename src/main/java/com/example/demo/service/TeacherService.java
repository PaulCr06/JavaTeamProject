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
    private final TeachingAssignmentService teachingAssignmentService;

    public TeacherService(TeacherRepository teacherRepository, TeachingAssignmentRepository teachingAssignmentRepository,
                          DepartmentRepository departmentRepository, TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.teachingAssignmentService = teachingAssignmentService;
    }

    public Teacher saveTeacher(Teacher teacher) {
        Department department = departmentRepository.findById(teacher.getDepartmentId());
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        if (department.getTeachers() == null) {
            department.setTeachers(new ArrayList<>());
        }
        department.getTeachers().add(savedTeacher);
        departmentRepository.update(department);

        return savedTeacher;
    }

    public Teacher updateTeacher(Teacher teacher) {
        Teacher existing = teacherRepository.findById(teacher.getId());
        if (existing == null) {
            return null;
        }

        if (departmentRepository.findById(teacher.getDepartmentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department with this ID does not exist!");
        }

        Department oldDepartment = departmentRepository.findById(existing.getDepartmentId());
        if (oldDepartment != null && oldDepartment.getTeachers() != null) {
            oldDepartment.getTeachers().removeIf(t -> t.getId().equals(existing.getId()));
            departmentRepository.update(oldDepartment);
        }

        teacher.setAssignments(existing.getAssignments());
        Teacher updatedTeacher = teacherRepository.update(teacher);
        Department targetDepartment = departmentRepository.findById(updatedTeacher.getDepartmentId());
        if (targetDepartment.getTeachers() == null) {
            targetDepartment.setTeachers(new ArrayList<>());
        }
        targetDepartment.getTeachers().add(updatedTeacher);
        departmentRepository.update(targetDepartment);
        return updatedTeacher;
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    public void deleteTeacher(String id) {
        Teacher existing = teacherRepository.findById(id);
        if (existing == null) {
            return;
        }

        Department department = departmentRepository.findById(existing.getDepartmentId());
        if (department != null && department.getTeachers() != null) {
            department.getTeachers().removeIf(t -> t.getId().equals(id));
            departmentRepository.update(department);
        }

        for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
            if (id.equals(assignment.getStaffId())) {
                teachingAssignmentService.deleteAssignment(assignment.getId());
            }
        }
        teacherRepository.delete(id);
    }
}
