package com.example.demo.controller;

import com.example.demo.model.Assistant;
import com.example.demo.model.Roles;
import com.example.demo.service.AssistantService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AssistantController {
    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/assistant")
    public String getAllAssistants(Model model) {
        model.addAttribute("assistants", assistantService.getAllAssistants());
        return "Assistant/index";
    }

    @GetMapping("/assistant/{id}")
    public String getAssistant(@PathVariable String id, Model model) {
        Assistant assistant = assistantService.getAssistantById(id);
        if (assistant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("assistant", assistant);
        return "Assistant/details";
    }

    @GetMapping("/assistant/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assistant", new Assistant());
        model.addAttribute("roleTypes", Roles.values());
        model.addAttribute("isUpdate", false);
        return "Assistant/form";
    }

    @GetMapping("/assistant/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Assistant assistant = assistantService.getAssistantById(id);
        if (assistant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("assistant", assistant);
        model.addAttribute("roleTypes", Roles.values());
        model.addAttribute("isUpdate", true);
        return "Assistant/form";
    }

    @PostMapping("/assistant")
    public String saveAssistant(@ModelAttribute Assistant assistant) {
        assistantService.saveAssistant(assistant);
        return "redirect:/assistant";
    }

    @PostMapping("/assistant/{id}")
    public String updateAssistant(@PathVariable String id, @ModelAttribute Assistant assistant) {
        assistant.setId(id);
        if (assistantService.updateAssistant(assistant) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/assistant";
    }

    @PostMapping("/assistant/{id}/delete")
    public String deleteAssistant(@PathVariable String id) {
        assistantService.deleteAssistant(id);
        return "redirect:/assistant";
    }
}
