package com.pulbatte.pulbatte.plantSearch.service;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plant.repository.PlantRepository;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.repository.PlantQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantSearchService {

    private final PlantQueryRepository queryRepository;
    private final PlantRepository plantRepository;

    public List<PlantSearchDto> findByPlantName(String keyword) {
        List<PlantSearchDto> plants = queryRepository.findByPlantName(keyword);
        for(PlantSearchDto plant : plants) {
            String plantName = plant.getPlantName();
            if(plantName.contains(keyword)) {
                plants.add(plant);
            }
        }
        return plants;
    }

    public List<PlantSearchDto> findByPlantTag(PlantTag tag) {
        return queryRepository.findByPlantTag(tag);
    }
}
