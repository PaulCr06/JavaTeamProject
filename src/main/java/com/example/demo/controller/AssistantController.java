package com.example.demo.controller;

import com.example.demo.service.AssistantService;
import org.springframework.stereotype.Controller;

@Controller
public class AssistantController {
    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }
}