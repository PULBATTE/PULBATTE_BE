package com.pulbatte.pulbatte.alarm.repository;

import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.dto.QAlarmResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.pulbatte.pulbatte.alarm.entity.QAlarm.alarm;
import static com.pulbatte.pulbatte.user.entity.QUser.user;

@Repository
public class AlarmQueryRepository {
    private final JPAQueryFactory queryFactory;

    public AlarmQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<AlarmResponseDto> findAllByUserId(@Param("userId") Long userId) {
        return queryFactory
                .select(new QAlarmResponseDto(
                        alarm.id,
                        alarm.content,
                        alarm.alarmType,
                        alarm.isRead
                ))
                .from(alarm)
                .join(alarm.user, user)
                .where(eqUserId(userId), alarm.isRead.eq(false))
                .orderBy(alarm.createdAt.asc())
                .fetch();
    }

    public Long changeState(Long userId) {
        return queryFactory
                .update(alarm)
                .set(alarm.isRead, true)
                .where(eqUserId(userId))
                .execute();
    }

    private BooleanExpression eqUserId(Long userId) {
        if(ObjectUtils.isEmpty(userId)) {
            return null;
        }
        return user.id.eq(userId);
    }
}
