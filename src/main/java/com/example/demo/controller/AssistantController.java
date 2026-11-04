package com.example.demo.controller;

import com.example.demo.model.Assistant;
import com.example.demo.service.AssistantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssistantController {
    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/assistant")
    public String getAllAssistants(Model model) {
        model.addAttribute("assistants", assistantService.getAllAssistants());
        return "assistant/index";
    }

    @GetMapping("/assistant/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assistant", new Assistant());
        return "assistant/form";
    }

    @PostMapping("/assistant")
    public String saveAssistant(@ModelAttribute Assistant assistant) {
        assistantService.saveAssistant(assistant);
        return "redirect:/assistant";
    }

    @PostMapping("/assistant/{id}/delete")
    public String deleteAssistant(@PathVariable String id) {
        assistantService.deleteAssistant(id);
        return "redirect:/assistant";
    }
}