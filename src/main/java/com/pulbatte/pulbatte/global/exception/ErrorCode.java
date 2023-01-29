package com.pulbatte.pulbatte.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //BAD_REQUEST
    DISMATCH_ADMIN_TOKEN(HttpStatus.BAD_REQUEST, "관리자 암호가 틀려 등록이 불가능합니다."),
    ALREADY_EXIST_USERNAME(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    DISMATCH_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    DISMATCH_TOKEN(HttpStatus.BAD_REQUEST,"토큰이 일치하지 않습니다."),

    // NOT_FOUND
    NO_POST_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    NO_PLANT_FOUND(HttpStatus.NOT_FOUND, "식물이 존재하지 않습니다."),
    NO_PLANT_JOURNAL_FOUND(HttpStatus.NOT_FOUND,"식물 일지가 존재하지 않습니다"),
    NO_PLANT_JOURNAL_DIARY_FOUND(HttpStatus.NOT_FOUND,"식물 일지 다이어리가 존재하지 않습니다."),
    NO_EXIST_USER(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),
    NO_BEGINNER_PLANT(HttpStatus.NOT_FOUND,"일치하는 초보자용 식물이 없습니다."),
    NO_TEST_RESULT(HttpStatus.NOT_FOUND,"일치하는 테스트 결과가 없습니다."),

    NO_LOCAL_USER(HttpStatus.NOT_FOUND, "허용된 로그인 방식이 아닙니다."),
    NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    NO_EXIST_CLICKTAG(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),

    // UNAUTHORIZED
    NO_MODIFY_POST(HttpStatus.UNAUTHORIZED,"게시글 수정 권한이 없습니다."),
    NO_MODIFY_COMMENT(HttpStatus.UNAUTHORIZED,"댓글 수정 권한이 없습니다."),
    NO_DELETE_POST(HttpStatus.UNAUTHORIZED,"게시글 삭제 권한이 없습니다."),
    NO_DELETE_COMMENT(HttpStatus.UNAUTHORIZED,"댓글 삭제 권한이 없습니다."),

    // CONFLICT
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),
    ALREADY_CLICKED_LIKE(HttpStatus.CONFLICT, "이미 좋아요를 눌렀습니다"),
    ALERADY_CANCEL_LIKE(HttpStatus.CONFLICT, "이미 좋아요 취소를 눌렀습니다"),

    // D-DAy
    ALREADY_WATER_CILCK(HttpStatus.CONFLICT, "이미 물 주기 버튼을 클릭 했습니다"),
    ALREADY_NUTRITION_CILCK(HttpStatus.CONFLICT, "이미 영양 주기 버튼을 클릭 했습니다"),
    ALREADY_REPOTTING_CILCK(HttpStatus.CONFLICT, "이미 분갈이 버튼을 클릭 했습니다"),
    ALREADY_DDAY_CLICK(HttpStatus.CONFLICT, "이미 디데이 버튼을 클릭 했습니다"),
    NO_DDAY(HttpStatus.BAD_REQUEST, "DDay가 아닙니다"),
    NO_WATER_D_DAY(HttpStatus.BAD_REQUEST, "물 주는 날이 아닙니다."),
    NO_NUTRITION_D_DAY(HttpStatus.BAD_REQUEST, "영양제 주는 날이 아닙니다."),
    NO_REPOTTING_D_DAY(HttpStatus.BAD_REQUEST, "분갈이 하는 날이 아닙니다."),

    ALREADY_EXIST_DATE(HttpStatus.BAD_REQUEST,"중복된 데이터입니다"),
    END_GUIDE(HttpStatus.BAD_REQUEST,"가이드가 종료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}