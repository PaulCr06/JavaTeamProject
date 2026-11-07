package com.example.demo.controller;


import com.example.demo.model.Room;
import com.example.demo.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/room/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        return "Room/form";
    }

    @PostMapping("/room")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/room";
    }

    @PostMapping("/room/{id}/delete")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return "redirect:/room";
    }
}
