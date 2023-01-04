package com.pulbatte.pulbatte.plant.service;


import com.pulbatte.pulbatte.plant.dto.PlantResponseDto;
import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.repository.PlantRepository;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    public List<PlantResponseDto> getListPlant(User user) {
        List<Plant> plantList = plantRepository.findAll();
        List<PlantResponseDto> plantResponseDto = new ArrayList<>();

        for(Plant plant : plantList){
            plantResponseDto.add(new PlantResponseDto(plant));
        }
        return plantResponseDto;
    }
}
