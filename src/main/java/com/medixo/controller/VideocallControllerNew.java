package com.medixo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VideocallControllerNew {

    @GetMapping("/video-call/{id}")
    public String videoCall(@PathVariable Long id, Model model) {

        String roomId = "medixo_room_" + id;

        model.addAttribute("roomId", roomId);

        return "video_call";
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "WORKING";
    }
    
}