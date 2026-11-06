package com.example.demo.service;

import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public Room getRoom(String roomId) {
        return roomRepository.findById(roomId);
    }
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    public void deleteRoom(String id) {
        roomRepository.delete(id);
    }
}
