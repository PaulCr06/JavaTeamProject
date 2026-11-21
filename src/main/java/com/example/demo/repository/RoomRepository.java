package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository extends InFileRepository<Room, String> {
    public RoomRepository() {
        super("src/main/resources/data/room.json", Room.class, "r");
    }

    public RoomRepository(String filePath) {
        super(filePath, Room.class, "r");
    }
}
