package com.example.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class Controller {
    private final OlchaService olchaService;

    @GetMapping
    public String get(){
        olchaService.requestForProductURL();
        return "Ok";
    }
}
