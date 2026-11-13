package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.example.demo.model.University;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.TeacherRepository;
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

    public DepartmentService(DepartmentRepository departmentRepository, CourseService courseService, TeacherService teacherService, UniversityRepository universityRepository) {
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
            University university = universities.get(0); // O luăm pe prima


            if (university.getDepartments() == null) {
                university.setDepartments(new ArrayList<>());
            }
            university.getDepartments().add(savedDepartment);
        }

        return savedDepartment;
    }
    public void deleteDepartment(String id) {
        Department department = departmentRepository.findById(id);
        for(Course course : department.getCourses()) {
            courseService.deleteCourse(course.getId());
        }
        for(Teacher teacher : department.getTeachers()) {
            teacherService.deleteTeacher(teacher.getId());
        }

        departmentRepository.delete(id);
    }
}
