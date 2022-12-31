package com.pulbatte.pulbatte.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SIGN_UP(HttpStatus.OK, "회원가입에 성공했습니다."),
    LOG_IN(HttpStatus.OK, "로그인에 성공했습니다"),
    LIKE(HttpStatus.OK, "좋아요 성공"),
    CANCEL_LIKE(HttpStatus.OK, "좋아요 취소"),
    UPLOAD_PROFILE(HttpStatus.OK, "프로필 사진 등록 완료"),

    UPLOAD_NICKNAME(HttpStatus.OK, "닉네임 변경 완료"),
    UPLOAD_JOBANDCAREER(HttpStatus.OK, "직무/경력 등록 완료"),
    DELETE_BOARD(HttpStatus.OK, "게시글을 삭제하였습니다"),
    DELETE_COMMENT(HttpStatus.OK, "댓글을 삭제하였습니다"),
    DELETE_USER(HttpStatus.OK,"회원 탈퇴를 완료하였습니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}