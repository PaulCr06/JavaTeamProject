package com.example.demo.service;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;
    private TeachingAssignmentRepository teachingAssignmentRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherService(TeacherRepository teacherRepository, TeachingAssignmentRepository teachingAssignmentRepository, DepartmentRepository departmentRepository) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    public Teacher saveTeacher(Teacher teacher){
        if (departmentRepository.findById(teacher.getDepartmentId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eroare: Departamentul cu acest ID nu există!");
        }

        return teacherRepository.save(teacher);


    }

    public List<Teacher> findAllTeachers(){
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(String id){
        return teacherRepository.findById(id);
    }

    public void deleteTeacher(String id){
        for(TeachingAssignment assignment : teachingAssignmentRepository.findAll()){
            if(id.equals(assignment.getStaffId())){
                teachingAssignmentRepository.delete(assignment.getId());
            }
        }
        teacherRepository.delete(id);
    }
}
