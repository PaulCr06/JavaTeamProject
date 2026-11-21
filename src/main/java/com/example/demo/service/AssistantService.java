package com.example.demo.service;

import com.example.demo.model.Assistant;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.AssistantRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    private void validateAssistant(Assistant assistant) {
        if (assistant == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Assistant cannot be null!");
        }
        if (assistant.getName() == null || assistant.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Assistant name must not be empty!");
        }
        if (assistant.getRole() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Assistant role must be selected!");
        }
    }

    public Assistant saveAssistant(Assistant assistant) {
        validateAssistant(assistant);
        return assistantRepository.save(assistant);
    }

    public Assistant updateAssistant(Assistant assistant) {
        if (assistant == null || assistant.getId() == null) {
            return null;
        }
        validateAssistant(assistant);

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
        Assistant assistant = assistantRepository.findById(id);
        if (assistant != null && teachingAssignmentRepository != null) {
            List<TeachingAssignment> assignments = new ArrayList<>();
            for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
                if (id.equals(assignment.getStaffId())) {
                    assignments.add(assignment);
                }
            }
            assistant.setAssignments(assignments);
        }
        return assistant;
    }

    public void deleteAssistant(String id) {
        if (assistantRepository.findById(id) == null) {
            return;
        }

        if (teachingAssignmentRepository != null && teachingAssignmentService != null) {
            for (TeachingAssignment assignment : teachingAssignmentRepository.findAll()) {
                if (id.equals(assignment.getStaffId())) {
                    teachingAssignmentService.deleteAssignment(assignment.getId());
                }
            }
        }
        assistantRepository.delete(id);
    }
}
