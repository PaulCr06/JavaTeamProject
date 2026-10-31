package com.example.demo.service;

import com.example.demo.model.Assistant;
import com.example.demo.repository.AssistantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantService {
    private final AssistantRepository assistantRepository;

    public AssistantService(AssistantRepository assistantRepository) {
        this.assistantRepository = assistantRepository;
    }

    public Assistant saveAssistant(Assistant assistant) {
        return assistantRepository.save(assistant);
    }

    public List<Assistant> getAllAssistants() {
        return assistantRepository.findAll();
    }

    public Assistant getAssistantById(String id) {
        return assistantRepository.findById(id);
    }

    public void deleteAssistant(String id) {
        assistantRepository.delete(id);
    }
}