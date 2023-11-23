package com.study.example.demaker.dto;

import com.study.example.demaker.entity.Developer;
import com.study.example.demaker.type.DeveloperLevel;
import com.study.example.demaker.type.DeveloperSkillType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class CreateDeveloper {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull
        @Min(0)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "invalid memberId")
        private String memberId;

        @NotNull
        @Size(min = 2, max = 50, message = "invalid name")
        private String name;

        @NotNull
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;
        private String name;
        private Integer age;

        // developer를 생성한 직후에 바로 developer 엔티티를 통해서 만들어줄 것이기 때문에
        // developer Entity와 상당히 강한 결합을 하게 됨

        // -> 자주 쓰는 방법 : fromEntity라는 이름으로 developer Entity를 받아서
        // 바로 Response를 만들어주는 static 메소드 사용
        public static Response fromEntity(@NonNull Developer developer) {
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYears())
                    .memberId(developer.getMemberId())
                    .name(developer.getName())
                    .age(developer.getAge())
                    .build();
        }
    }
}
