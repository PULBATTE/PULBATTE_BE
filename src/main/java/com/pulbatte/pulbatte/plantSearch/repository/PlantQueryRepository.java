package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plant.entity.QPlant;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.dto.QPlantListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pulbatte.pulbatte.plant.entity.QPlant.plant;


@Repository
public class PlantQueryRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public PlantQueryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    // 전체 목록 가져오기
    public List<PlantListDto> findAll() {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .orderBy(plant.plantName.desc())
                .fetch();
    }

    // 식물 이름 검색
    public List<PlantListDto> findByPlantName(PlantSearchDto searchDto) {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantName(searchDto.getPlantName()))
                .orderBy(plant.plantName.desc())
                .fetch();
    }

    // 태그 필터링
    public List<PlantListDto> findByPlantTag(PlantSearchDto searchDto) {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantTag(searchDto.getPlantTag()))
                .orderBy(plant.plantName.desc())
                .fetch();
    }

    private BooleanExpression eqPlantName(String plantName) {
        if(ObjectUtils.isEmpty(plantName)) {
            return null;
        }
        return plant.plantName.contains(plantName);
    }

    private BooleanExpression eqPlantTag(PlantTag tag) {
        if(ObjectUtils.isEmpty(tag)) {
            return null;
        }
        return plant.plantTag.eq(tag);
    }

}
