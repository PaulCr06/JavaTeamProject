package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CourseService courseService;
    private final TeacherService teacherService;

    public DepartmentService(DepartmentRepository departmentRepository, CourseService courseService, TeacherService teacherService) {
        this.departmentRepository = departmentRepository;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
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
