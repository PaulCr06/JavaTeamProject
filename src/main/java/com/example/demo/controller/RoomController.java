package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/room")
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", roomService.getRooms());
        return "Room/index";
    }

    @GetMapping("/room/{id}")
    public String getRoom(@PathVariable String id, Model model) {
        Room room = roomService.getRoom(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("room", room);
        return "Room/details";
    }

    @GetMapping("/room/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("isUpdate", false);
        return "Room/form";
    }

    @GetMapping("/room/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Room room = roomService.getRoom(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("room", room);
        model.addAttribute("isUpdate", true);
        return "Room/form";
    }

    @PostMapping("/room")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/room";
    }

    @PostMapping("/room/{id}")
    public String updateRoom(@PathVariable String id, @ModelAttribute Room room) {
        room.setId(id);
        if (roomService.updateRoom(room) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/room";
    }

    @PostMapping("/room/{id}/delete")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return "redirect:/room";
    }
}
