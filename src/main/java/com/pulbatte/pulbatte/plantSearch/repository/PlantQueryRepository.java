package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.dto.QPlantSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.pulbatte.pulbatte.plant.entity.QPlant.plant;

@RequiredArgsConstructor
@Repository
public class PlantQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PlantSearchDto> findByPlantName(String plantName) {
        return queryFactory
                .select(new QPlantSearchDto(
                        plant.id,
                        plant.plantName,
                        plant.plantTag,
                        plant.image
                ))
                .from(plant)
                .where(eqPlantName(plantName))
                .fetch();
    }

    public List<PlantSearchDto> findByPlantTag(PlantTag tag) {
        return queryFactory
                .select(new QPlantSearchDto(
                        plant.id,
                        plant.plantName,
                        plant.plantTag,
                        plant.image
                ))
                .from(plant)
                .where(eqPlantTag(tag))
                .fetch();
    }

    private BooleanExpression eqPlantName(String plantName) {
        if(!StringUtils.hasText(plantName)) {
            return null;
        }
        return plant.plantName.eq(plantName);
    }

    private BooleanExpression eqPlantTag(PlantTag tag) {
        if(ObjectUtils.isEmpty(tag)) {
            return null;
        }
        return plant.plantTag.eq(tag);
    }

}
