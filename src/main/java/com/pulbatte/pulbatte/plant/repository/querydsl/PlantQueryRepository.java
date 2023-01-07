package com.pulbatte.pulbatte.plant.repository.querydsl;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pulbatte.pulbatte.plant.entity.QPlant.plant;

@RequiredArgsConstructor
@Repository
public class PlantQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Plant> findByPlantTag(PlantTag tag) {
        return queryFactory
                .selectFrom(plant)
                .where(eqPlantTag(tag))
                .fetch();
    }

    private BooleanExpression eqPlantTag(PlantTag tag) {
        return plant.plantTag.eq(tag);
    }

}
