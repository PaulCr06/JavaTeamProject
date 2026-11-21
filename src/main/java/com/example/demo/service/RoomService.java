package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Room;
import com.example.demo.model.University;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UniversityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Room room = roomRepository.findById(roomId);
        if (room != null) {
            List<Course> roomCourses = new ArrayList<>();
            for (Course course : courseService.getAllCourses()) {
                if (roomId.equals(course.getRoomId())) {
                    roomCourses.add(course);
                }
            }
            room.setCourses(roomCourses);
        }
        return room;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room cannot be null!");
        }
        if (room.getNumber() == null || room.getNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room number must not be empty!");
        }
        if (room.getCapacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room capacity must be positive!");
        }

        Room savedRoom = roomRepository.save(room);

        List<University> universities = universityRepository.findAll();
        if (!universities.isEmpty()) {
            University university = universities.get(0);
            if (university.getRooms() == null) {
                university.setRooms(new ArrayList<>());
            }
            university.getRooms().add(savedRoom);
            universityRepository.update(university);
        }

        return savedRoom;
    }

    public Room updateRoom(Room room) {
        if (room == null || room.getId() == null) {
            return null;
        }
        if (room.getNumber() == null || room.getNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room number must not be empty!");
        }
        if (room.getCapacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Room capacity must be positive!");
        }

        Room existing = roomRepository.findById(room.getId());
        if (existing == null) {
            return null;
        }

        room.setCourses(existing.getCourses());
        Room updatedRoom = roomRepository.update(room);

        for (University university : universityRepository.findAll()) {
            if (university.getRooms() != null
                    && university.getRooms().stream().anyMatch(r -> r.getId().equals(existing.getId()))) {
                university.getRooms().replaceAll(r ->
                        r.getId().equals(updatedRoom.getId()) ? updatedRoom : r);
                universityRepository.update(university);
            }
        }

        return updatedRoom;
    }

    public void deleteRoom(String id) {
        Room room = roomRepository.findById(id);
        if (room == null) {
            return;
        }

        for (Course course : courseService.getAllCourses()) {
            if (id.equals(course.getRoomId())) {
                courseService.deleteCourse(course.getId());
            }
        }

        for (University university : universityRepository.findAll()) {
            if (university.getRooms() != null
                    && university.getRooms().removeIf(r -> r.getId().equals(id))) {
                universityRepository.update(university);
            }
        }

        roomRepository.delete(id);
    }
}
