package com.example.webhooks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class WebhookController {
    private String TOKEN = "TOKEN";
    private String SUBSCRIBE_MODE = "subscribe";

    @GetMapping
    public String hello() {
        return "Hello :)";
    }

    @GetMapping("/webhooks")
    public ResponseEntity<Integer> verify(@RequestParam("hub.mode") String mode,
                                          @RequestParam("hub.challenge") int challenge,
                                          @RequestParam("hub.verify_token") String verifyToken) {
        if (SUBSCRIBE_MODE.equals(mode) && TOKEN.equals(verifyToken)) {
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/webhooks")
    public ResponseEntity<Void> processNotification(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        log.info("Body: " + body);
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
