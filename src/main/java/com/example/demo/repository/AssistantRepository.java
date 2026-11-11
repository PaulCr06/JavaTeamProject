package com.example.demo.repository;

import com.example.demo.model.Assistant;
import org.springframework.stereotype.Repository;

@Repository
public class AssistantRepository extends InFileRepository<Assistant, String> {

    public AssistantRepository(){
        super("src/main/resources/data/assistant.json", Assistant.class);
    }
}