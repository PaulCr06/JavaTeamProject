package com.example.demo.service;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.model.Teacher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository){
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
        teacherRepository.delete(id);
    }
}
