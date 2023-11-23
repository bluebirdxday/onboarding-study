package com.study.example.demaker.dto;

import com.study.example.demaker.entity.Developer;
import com.study.example.demaker.type.DeveloperLevel;
import com.study.example.demaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;

    // 세부적이지 않은, 심플한 정보를 담아주는 디벨로퍼 DTO 생성
    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}