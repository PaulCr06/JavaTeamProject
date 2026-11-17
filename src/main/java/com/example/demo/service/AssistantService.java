package com.example.demo.service;

import com.example.demo.model.Assistant;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantService {
    private final AssistantRepository assistantRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final TeachingAssignmentService teachingAssignmentService;

    public AssistantService(AssistantRepository assistantRepository, TeachingAssignmentRepository teachingAssignmentRepository,
                            TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.assistantRepository = assistantRepository;
        this.teachingAssignmentService = teachingAssignmentService;
    }

    public Assistant saveAssistant(Assistant assistant) {
        return assistantRepository.save(assistant);
    }

    public Assistant updateAssistant(Assistant assistant) {
        Assistant existing = assistantRepository.findById(assistant.getId());
        if (existing == null) {
            return null;
        }
        assistant.setAssignments(existing.getAssignments());
        return assistantRepository.update(assistant);
    }

    public List<Assistant> getAllAssistants() {
        return assistantRepository.findAll();
    }

    public Assistant getAssistantById(String id) {
        return assistantRepository.findById(id);
    }

    public void deleteAssistant(String id) {
        if (assistantRepository.findById(id) == null) {
            return;
        }

        for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
            if (id.equals(assignment.getStaffId())) {
                teachingAssignmentService.deleteAssignment(assignment.getId());
            }
        }
        assistantRepository.delete(id);
    }
}
