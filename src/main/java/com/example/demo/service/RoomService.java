package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Room;
import com.example.demo.model.University;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final CourseService courseService;
    private final UniversityRepository universityRepository;

    public RoomService(RoomRepository roomRepository, CourseService courseService, UniversityRepository universityRepository) {
        this.roomRepository = roomRepository;
        this.courseService = courseService;
        this.universityRepository = universityRepository;
    }

    public Room getRoom(String roomId) {
        return roomRepository.findById(roomId);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {
        Room savedRoom = roomRepository.save(room);

        if (!universityRepository.findAll().isEmpty()) {
            University university = universityRepository.findAll().get(0);
            if (university.getRooms() == null) {
                university.setRooms(new ArrayList<>());
            }
            university.getRooms().add(savedRoom);
        }

        return savedRoom;
    }

    public Room updateRoom(Room room) {
        Room existing = roomRepository.findById(room.getId());
        if (existing == null) {
            return null;
        }

        room.setCourses(existing.getCourses());
        return roomRepository.update(room);
    }

    public void deleteRoom(String id) {
        Room room = roomRepository.findById(id);
        if (room == null) {
            return;
        }

        if (room.getCourses() != null) {
            for (Course course : new ArrayList<>(room.getCourses())) {
                courseService.deleteCourse(course.getId());
            }
        }

        roomRepository.delete(id);
    }
}
