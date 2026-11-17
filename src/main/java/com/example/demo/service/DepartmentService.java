package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.example.demo.model.University;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final UniversityRepository universityRepository;

    public DepartmentService(DepartmentRepository departmentRepository, CourseService courseService,
                             TeacherService teacherService, UniversityRepository universityRepository) {
        this.departmentRepository = departmentRepository;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.universityRepository = universityRepository;
    }

    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department saveDepartment(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        List<University> universities = universityRepository.findAll();

        if (!universities.isEmpty()) {
            University university = universities.get(0);
            if (university.getDepartments() == null) {
                university.setDepartments(new ArrayList<>());
            }
            university.getDepartments().add(savedDepartment);
            universityRepository.update(university);
        }

        return savedDepartment;
    }

    public Department updateDepartment(Department department) {
        Department existing = departmentRepository.findById(department.getId());
        if (existing == null) {
            return null;
        }

        department.setCourses(existing.getCourses());
        department.setTeachers(existing.getTeachers());

        Department updatedDepartment = departmentRepository.update(department);
        for (University university : universityRepository.findAll()) {
            if (university.getDepartments() != null
                    && university.getDepartments().stream().anyMatch(d -> d.getId().equals(existing.getId()))) {
                university.getDepartments().replaceAll(d ->
                        d.getId().equals(updatedDepartment.getId()) ? updatedDepartment : d);
                universityRepository.update(university);
            }
        }

        return updatedDepartment;
    }

    public void deleteDepartment(String id) {
        Department department = departmentRepository.findById(id);
        if (department == null) {
            return;
        }

        if (department.getCourses() != null) {
            for (Course course : new ArrayList<>(department.getCourses())) {
                courseService.deleteCourse(course.getId());
            }
        }

        if (department.getTeachers() != null) {
            for (Teacher teacher : new ArrayList<>(department.getTeachers())) {
                teacherService.deleteTeacher(teacher.getId());
            }
        }

        for (University university : universityRepository.findAll()) {
            if (university.getDepartments() != null
                    && university.getDepartments().removeIf(d -> d.getId().equals(id))) {
                universityRepository.update(university);
            }
        }

        departmentRepository.delete(id);
    }
}
