package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.example.demo.model.University;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Department department = departmentRepository.findById(id);
        if (department != null) {
            List<Course> deptCourses = new ArrayList<>();
            for (Course course : courseService.getAllCourses()) {
                if (id.equals(course.getDepartmentId())) {
                    deptCourses.add(course);
                }
            }
            department.setCourses(deptCourses);

            List<Teacher> deptTeachers = new ArrayList<>();
            for (Teacher teacher : teacherService.findAllTeachers()) {
                if (id.equals(teacher.getDepartmentId())) {
                    deptTeachers.add(teacher);
                }
            }
            department.setTeachers(deptTeachers);
        }
        return department;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department saveDepartment(Department department) {
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department cannot be null!");
        }
        if (department.getName() == null || department.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department name must not be empty!");
        }

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
        if (department == null || department.getId() == null) {
            return null;
        }
        if (department.getName() == null || department.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Department name must not be empty!");
        }

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

        for (Course course : courseService.getAllCourses()) {
            if (id.equals(course.getDepartmentId())) {
                courseService.deleteCourse(course.getId());
            }
        }

        for (Teacher teacher : teacherService.findAllTeachers()) {
            if (id.equals(teacher.getDepartmentId())) {
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
