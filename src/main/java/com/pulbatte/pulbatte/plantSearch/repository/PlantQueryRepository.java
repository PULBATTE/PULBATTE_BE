package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plantSearch.dto.PlantDetailDto;
import com.pulbatte.pulbatte.plantSearch.dto.QPlantDetailDto;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.dto.QPlantListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.pulbatte.pulbatte.plantSearch.entity.QPlant.plant;


@Repository
public class PlantQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PlantQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 전체 목록 가져오기
    public List<PlantListDto> findAll() {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .orderBy(plant.plantName.asc())
                .fetch();
    }

    // 단건 조회
    public PlantDetailDto findOne(Long plantId) {
        return queryFactory
                .select(new QPlantDetailDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantId(plantId))
                .fetchOne();
    }

    // 식물 이름 검색
    public List<PlantListDto> findByPlantName(@Param("plantName") String plantName) {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantName(plantName))
                .orderBy(plant.plantName.asc())
                .fetch();
    }

    // 태그 필터링
    public List<PlantListDto> findByPlantTag(PlantTag tag) {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantTag(tag))
                .orderBy(plant.plantName.asc())
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

    private BooleanExpression eqPlantId(Long plantId) {
        if(ObjectUtils.isEmpty(plantId)) {
            return null;
        }
        return plant.id.eq(plantId);
    }

}
