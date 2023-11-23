package com.study.example.demaker.dto;

import com.study.example.demaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 실무에서는 이렇게 많이 진행
// api별로 성공할 때 내려주는 응답들은 다 다른 방식으로 하고 성공 외 실패로 내려가는 경우
// 이런 식으로 공통 실패 dto를 하나 만들어놓고 구조에 맞춰서 내려주는 방식으로 많이 진행함
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
