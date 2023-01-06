package com.pulbatte.pulbatte.plant.repository;

import com.pulbatte.pulbatte.plant.dto.PlantListDto;
import com.pulbatte.pulbatte.plant.entity.PlantSearchCondition;

import java.util.List;

public interface PlantRepositoryCustom {

    List<PlantListDto> search(PlantSearchCondition condition);
}
