package com.pulbatte.pulbatte.alarm.repository;

import com.pulbatte.pulbatte.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
