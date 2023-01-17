package com.pulbatte.pulbatte.alarm.repository;

import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.dto.QAlarmResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pulbatte.pulbatte.alarm.entity.QAlarm.alarm;
import static com.pulbatte.pulbatte.user.entity.QUser.user;

@Repository
public class AlarmQueryRepository {
    private final JPAQueryFactory queryFactory;
    public AlarmQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<AlarmResponseDto> findByUser() {
        return queryFactory
                .select(new QAlarmResponseDto(
                        alarm
                ))
                .from(alarm)
                .join(alarm.user, user)
                .orderBy(alarm.createdAt.asc())
                .fetch();
    }
}
