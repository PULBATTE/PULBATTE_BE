package com.pulbatte.pulbatte.alarm.entity;

import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private User user;

    @Builder
    public Alarm(AlarmType alarmType, String content, User user) {
        this.alarmType = alarmType;
        this.content = content;
        this.user = user;
    }
}
