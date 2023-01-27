package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plantSearch.dto.*;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
//    public List<PlantListDto> findAll() {
//        return queryFactory
//                .select(new QPlantListDto(
//                        plant
//                ))
//                .from(plant)
//                .orderBy(plant.plantName.asc())
//                .fetch();
//    }

    // 전체 목록 무한 스크롤
    public Slice<PlantListDto> findAllBySlice(Pageable pageable) {
        List<PlantListDto> results = queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .orderBy(plant.plantName.asc())
                .limit(pageable.getPageSize()+1)            // 다음 페이지가 있는지 판단
                .fetch();
        return checkLastPage(pageable, results);
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
                .fetch();
    }

    // 태그 필터링
    public Slice<PlantListDto> findByPlantTag(PlantTag tag, Pageable pageable) {
        List<PlantListDto> results = queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(eqPlantTag(tag))
                .orderBy(plant.plantName.asc())
                .limit(pageable.getPageSize()+1)            // 다음 페이지가 있는지 판단
                .fetch();
        return checkLastPage(pageable, results);
    }

    // 초보자 태그
    public List<PlantListDto> findByBeginnerTag(int beginner) {
        return queryFactory
                .select(new QPlantListDto(
                        plant
                ))
                .from(plant)
                .where(isBeginner(beginner))
                .orderBy(plant.plantName.asc())
                .fetch();
    }

//    private BooleanExpression eqPlantName(String plantName) {
//        if(ObjectUtils.isEmpty(plantName)) {
//            return null;
//        }
//        return plant.plantName.contains(plantName);
//    }

    // FullText Index 적용
    private BooleanExpression eqPlantName(String plantName) {
        if(ObjectUtils.isEmpty(plantName)) {
            return null;
        }
        NumberTemplate booleanTemplate = Expressions.numberTemplate(
                Double.class,
                "function('match',{0},{1})", plant.plantName, plantName);
        return booleanTemplate.gt(0);
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

    private BooleanExpression isBeginner(int beginner) {
        if(beginner==0) {
            return plant.beginner.eq(0);
        }
        else return plant.beginner.eq(1);
    }

    // 무한 스크롤 처리
    private Slice<PlantListDto> checkLastPage(Pageable pageable, List<PlantListDto> results) {
        boolean hasNext = false;            // 다음 페이지가 있는지 여부

        if(results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }

}
