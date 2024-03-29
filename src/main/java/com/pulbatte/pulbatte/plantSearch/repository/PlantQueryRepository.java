package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plantSearch.dto.*;
import com.pulbatte.pulbatte.plantSearch.entity.CustomPageImpl;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.pulbatte.pulbatte.plantSearch.entity.QPlant.plant;
import static org.springframework.util.ObjectUtils.isEmpty;


@Repository
public class PlantQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PlantQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public CustomPageImpl<PlantListDto> findAll(Long cursorId, Pageable pageable) {
        List<PlantListDto> results = queryFactory
                .select(Projections.fields(PlantListDto.class,
                        plant.id,
                        plant.plantName,
                        plant.image
                ))
                .from(plant)
                .where(eqCursorId(cursorId))
                .orderBy(plant.plantName.asc())
                .limit(pageable.getPageSize())
                .fetch();

        return new CustomPageImpl<PlantListDto>(results, pageable, results.size());
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
                .select(Projections.fields(PlantListDto.class,
                        plant.id,
                        plant.plantName,
                        plant.image
                ))
                .from(plant)
                .where(eqPlantName(plantName))
                .fetch();
    }

    // 태그 필터링
    public CustomPageImpl<PlantListDto> findByPlantTag(PlantTag tag, Long cursorId, Pageable pageable) {
        List<PlantListDto> results = new ArrayList<>();

        if (tag.equals(PlantTag.beginner)) {
            int beginner = 1;
            results = queryFactory
                    .select(Projections.fields(PlantListDto.class,
                            plant.id,
                            plant.plantName,
                            plant.image
                    ))
                    .from(plant)
                    .where(isBeginner(beginner), eqCursorId(cursorId))
                    .orderBy(plant.plantName.asc())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else if (tag.equals(PlantTag.all)) {
            results = queryFactory
                    .select(Projections.fields(PlantListDto.class,
                            plant.id,
                            plant.plantName,
                            plant.image
                    ))
                    .from(plant)
                    .where(eqPlantTag(tag), eqCursorId(cursorId))
                    .orderBy(plant.plantName.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else {
            results = queryFactory
                    .select(Projections.fields(PlantListDto.class,
                            plant.id,
                            plant.plantName,
                            plant.image
                    ))
                    .from(plant)
                    .where(eqPlantTag(tag), eqCursorId(cursorId))
                    .orderBy(plant.plantName.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        }
        return new CustomPageImpl<PlantListDto>(results, pageable, results.size());
    }

    // 초보자 태그
    public Page<PlantListDto> findByBeginnerTag(int beginner, Pageable pageable) {
        List<PlantListDto> results = queryFactory
                .select(Projections.fields(PlantListDto.class,
                        plant.id,
                        plant.plantName,
                        plant.image
                ))
                .from(plant)
                .where(isBeginner(beginner))
                .orderBy(plant.plantName.asc())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(plant.count())
                .from(plant)
                .where(isBeginner(beginner))
                .fetchOne();

        return new PageImpl<>(results, pageable, count);
    }

    private BooleanExpression eqCursorId(Long cursorId) {
        return cursorId == null? null : plant.id.gt(cursorId);
    }

    // FullText Index 적용
    private BooleanExpression eqPlantName(String plantName) {
        if (isEmpty(plantName)) {
            return null;
        }
        NumberTemplate booleanTemplate = Expressions.numberTemplate(
                Double.class,
                "function('match',{0},{1})", plant.plantName, plantName);
        return booleanTemplate.gt(0);
    }

    private BooleanExpression eqPlantTag(PlantTag tag) {
        if (isEmpty(tag)) {
            return null;
        }
        return plant.plantTag.eq(tag);
    }

    private BooleanExpression eqPlantId(Long plantId) {
        if (isEmpty(plantId)) {
            return null;
        }
        return plant.id.eq(plantId);
    }

    private BooleanExpression isBeginner(int beginner) {
        if (beginner == 0) {
            return null;
        } else return plant.beginner.eq(1);
    }
}
