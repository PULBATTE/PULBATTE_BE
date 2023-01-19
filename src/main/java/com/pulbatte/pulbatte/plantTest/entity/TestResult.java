package com.pulbatte.pulbatte.plantTest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "testResult")
@Getter
@NoArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String resultCode;         // 테스트 결과 코드
    @Column
    private String resultTitle;        // 테스트 결과 성격 제목
    @Column
    private String resultImage;        // 테스트 결과 성격 이미지
    @Column
    private String resultString;       // 테스트 결과 성격 멘트
}
