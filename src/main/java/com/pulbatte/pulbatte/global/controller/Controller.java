package com.pulbatte.pulbatte.global.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

public class Controller {
    private final Environment env;

    public Controller(Environment env) {
        this.env = env;
    }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}
