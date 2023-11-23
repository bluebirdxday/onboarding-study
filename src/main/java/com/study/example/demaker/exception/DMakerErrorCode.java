package com.study.example.demaker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    NO_DEVELOPER("해당되는 개발자가 없습니다"),
    DUPLICATED_MEMBER_ID("MemeberId가 중복되는 개발자가 있습니다."),
    LEVEL_EXPERIENCE_YEAR_NO_MATCHED("개발자 레벨과 연차가 맞지 않습니다."),

    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    // 특정 API를 호출했을 때 타임아웃이 발생하거나 혹은 그 API 응답에서 특정 값이 있어야 되는데 없거나
    // 기타 등등,, 알 수 없는 에러들이 발생했을 때 어쩔 수 없이 써야 되는 에러
    INVALID_REQUEST("잘못된 요청입니다.");

    private final String message;
}
