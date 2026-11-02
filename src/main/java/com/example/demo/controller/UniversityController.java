package com.example.demo.controller;


import com.example.demo.service.RoomService;
import com.example.demo.service.UniversityService;
import org.springframework.stereotype.Controller;

@Controller
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }
}
