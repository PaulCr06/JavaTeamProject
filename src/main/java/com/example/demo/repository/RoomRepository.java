package com.example.demo.repository;
import com.example.demo.model.Room;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomRepository implements Interface<Room, String> {
    private final Map<String, Room> rooms = new HashMap<>();

    @Override
    public Room save(Room room) {
        rooms.put(room.getId(), room);
        return room;
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public Room findById(String id) {
        return rooms.get(id);
    }

    @Override
    public void delete(String id) {
        rooms.remove(id);
    }
}
