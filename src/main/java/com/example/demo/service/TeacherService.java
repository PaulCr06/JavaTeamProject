package com.example.demo.service;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;
    private TeachingAssignmentRepository teachingAssignmentRepository;

    public TeacherService(TeacherRepository teacherRepository, TeachingAssignmentRepository teachingAssignmentRepository){
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Teacher saveTeacher(Teacher teacher){
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
