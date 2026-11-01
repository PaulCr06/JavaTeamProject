package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

import java.util.List;

public class StudentService {

private  final StudentRepository studentRepository;

public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
}
public Student saveStudent(Student student) {
    return studentRepository.save(student);
}
public List<Student> findAllStudents() {
    return studentRepository.findAll();
}
public  void deleteStudent(String Id) {
    studentRepository.delete(Id);
}
public Student findStudentById(String Id) {
   return studentRepository.findById(Id);
}

}
