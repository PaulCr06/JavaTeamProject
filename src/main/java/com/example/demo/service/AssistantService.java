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

    public AssistantService(AssistantRepository assistantRepository, TeachingAssignmentRepository teachingAssignmentRepository) {
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.assistantRepository = assistantRepository;
    }

    public Assistant saveAssistant(Assistant assistant) {
        return assistantRepository.save(assistant);
    }
    public Assistant updateAssistant(Assistant assistant) {
        return assistantRepository.update(assistant);
    }

    public List<Assistant> getAllAssistants() {
        return assistantRepository.findAll();
    }

    public Assistant getAssistantById(String id) {
        return assistantRepository.findById(id);
    }

    public void deleteAssistant(String id) {
        for(TeachingAssignment assignment : teachingAssignmentRepository.findAll()){
            if(id.equals(assignment.getStaffId())){
                teachingAssignmentRepository.delete(assignment.getId());
            }
        }
        assistantRepository.delete(id);
    }
}