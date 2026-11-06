package com.example.demo.repository;
import com.example.demo.model.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository extends InMemoryRepository<Room, String>{
}
