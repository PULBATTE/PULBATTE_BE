package com.pulbatte.pulbatte.plantTest.repository;

import com.pulbatte.pulbatte.plantTest.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult,Long> {
    Optional<TestResult> findByResultCode(String resultCode);
}
