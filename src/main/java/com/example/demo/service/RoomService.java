package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Room;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final CourseService courseService;

    public RoomService(RoomRepository roomRepository, CourseService courseService) {
        this.roomRepository = roomRepository;
        this.courseService = courseService;
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
        Room room = roomRepository.findById(id);
        for (Course course : room.getCourses()) {
            courseService.deleteCourse(course.getId());
        }

        roomRepository.delete(id);
    }
}
