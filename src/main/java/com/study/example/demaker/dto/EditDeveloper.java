package com.study.example.demaker.dto;

import com.study.example.demaker.type.DeveloperLevel;
import com.study.example.demaker.type.DeveloperSkillType;
import lombok.*;

public class EditDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String name;
        private Integer age;
    }
}