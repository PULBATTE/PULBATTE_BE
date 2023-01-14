package com.pulbatte.pulbatte.alarm.entity;

import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

@Entity(name = "alarms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;            // 알림 타입 (댓글/일지)
    @Column(nullable = false)
    private String content;         // 알림 내용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Alarm(AlarmType alarmType, String content, User user) {
        this.alarmType = alarmType;
        this.content = content;
        this.user = user;
    }
}
