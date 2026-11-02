package com.example.demo.controller;


import com.example.demo.service.RoomService;
import org.springframework.stereotype.Controller;

@Controller
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
}
