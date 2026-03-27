package com.medixo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoCallController {

    private String offer;
    private String answer;

    @PostMapping("/send-offer")
    public String sendOffer(@RequestBody String offer) {
        this.offer = offer;
        return "Offer saved";
    }

    @GetMapping("/get-offer")
    public String getOffer() {
        return offer;
    }

    @PostMapping("/send-answer")
    public String sendAnswer(@RequestBody String answer) {
        this.answer = answer;
        return "Answer saved";
    }

    @GetMapping("/get-answer")
    public String getAnswer() {
        return answer;
    }
}