package com.example.demo.repository;


import com.example.demo.model.Assistant;
import com.example.demo.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherRepository extends FileRepository<Teacher, String>{}