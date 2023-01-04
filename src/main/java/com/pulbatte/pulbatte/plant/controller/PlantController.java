package com.pulbatte.pulbatte.plant.controller;

import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.PlantResponseDto;
import com.pulbatte.pulbatte.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @GetMapping("/all")
    public List<PlantResponseDto> getListPlant(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return plantService.getListPlant(userDetails.getUser());
    }

}
