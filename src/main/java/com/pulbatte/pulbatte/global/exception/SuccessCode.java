package com.pulbatte.pulbatte.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {


    //회원 가입 페이지
    SIGN_UP(HttpStatus.OK, "회원가입에 성공했습니다."),
    LOG_IN(HttpStatus.OK, "로그인에 성공했습니다"),
    CHECK_NICKNAME(HttpStatus.OK,"사용 가능한 닉네임입니다."),

    //마이 페이지
    UPLOAD_PROFILE(HttpStatus.OK, "프로필 사진 등록 완료"),
    UPLOAD_NICKNAME(HttpStatus.OK, "닉네임 변경 완료"),
    DELETE_USER(HttpStatus.OK,"회원 탈퇴를 완료하였습니다"),

    UPLOAD_JOBANDCAREER(HttpStatus.OK, "직무/경력 등록 완료"),

    //게시글
    CREATE_BOARD(HttpStatus.OK, "게시글을 등록하였습니다"),
    DELETE_BOARD(HttpStatus.OK, "게시글을 삭제하였습니다"),
    LIKE(HttpStatus.OK, "좋아요 성공"),
    CANCEL_LIKE(HttpStatus.OK, "좋아요 취소"),

    //댓글
    CREATE_COMMENT(HttpStatus.OK, "댓글을 등록하였습니다"),
    UPDATE_COMMENT(HttpStatus.OK, "댓글을 수정하였습니다"),
    DELETE_COMMENT(HttpStatus.OK, "댓글을 삭제하였습니다"),

    // D-Day 클릭
    DDAY_CLICK_OK(HttpStatus.OK, "Dday 클릭 완료!!"),
    WATER_CLICK_OK(HttpStatus.OK, "물 주기 완료!!"),
    NUTRITION_CLICK_OK(HttpStatus.OK, "영양제 주기 완료!!"),
    REPOTTING_CLICK_OK(HttpStatus.OK, "분갈이 완료!!"),

    // 식물 일지
    CREATE_PLANT_JOURNAL(HttpStatus.OK, "식물 일지를 등록 하였습니다."),
    UPDATE_PLANT_JOURNAL(HttpStatus.OK, "식물 일지를 수정 하였습니다."),
    DELETE_PLANT_JOURNAL(HttpStatus.OK, "식물 일지를 삭제 하였습니다."),

    // 식물 일지 다이어리
    CREATE_PLANT_JOURNAL_DIARY(HttpStatus.OK, "식물 일지 다이어리를 등록 하였습니다."),
    UPDATE_PLANT_JOURNAL_DIARY(HttpStatus.OK, "식물 일지 다이어리를 수정 하였습니다."),
    DELETE_PLANT_JOURNAL_DIARY(HttpStatus.OK, "식물 일지 다이어리를 삭제 하였습니다."),

    // 식집사 가이드
    CREATE_BEGINNER_GRAPH(HttpStatus.OK,"그래프를 등록 하였습니다."),
    CREATE_BEGINNER_Plant(HttpStatus.OK,"가이드 식물을 등록 하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}