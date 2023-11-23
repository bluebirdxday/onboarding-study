package com.study.example.demaker.service;

import com.study.example.demaker.code.StatusCode;
import com.study.example.demaker.dto.CreateDeveloper;
import com.study.example.demaker.dto.DeveloperDetailDto;
import com.study.example.demaker.dto.DeveloperDto;
import com.study.example.demaker.dto.EditDeveloper;
import com.study.example.demaker.entity.Developer;
import com.study.example.demaker.entity.RetiredDeveloper;
import com.study.example.demaker.exception.DMakerException;
import com.study.example.demaker.exception.DMakerErrorCode;
import com.study.example.demaker.repository.DeveloperRepository;
import com.study.example.demaker.repository.RetiredDeveloperRepository;
import com.study.example.demaker.type.DeveloperLevel;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.example.demaker.exception.DMakerErrorCode.NO_DEVELOPER;

// D-Maker의 서비스 레이어 역할을 함, 비즈니스 로직 담당
@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){

        validateCreateDeveloperRequest(request);
        // .builder() => Developer Entity에 @Builder 어노테이션을 적어놔서 쓸 수 있게 됨
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .status(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
        // entity 생성
        // 빌드를 완료한 developer를 엔티티 객체를 하나 만들어서
        // 디벨로퍼 리파지토리에서 save를 통해(영속화) db에 저장하는 로직

        developerRepository.save(developer);
        // developerRepository를 통해 db에 영속화

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(
            @NonNull CreateDeveloper.Request request){
        // 비지니스 유효성 검증 시행

        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);
                });

        if(developerLevel == DeveloperLevel.SENIOR
            && experienceYears < 10){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NO_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR
            && (experienceYears < 4 || request.getExperienceYears() > 10)){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NO_MATCHED);
        }

    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        // 받아온 developer를 Developer DTO로 바꿔주는 맵핑
        // .원하는 타입으로 (LIST DeveloperDto 타입)으로 변경
        return developerRepository.findDevelopersByStatusEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto DeveloperDetailDto(String memberId) {
        // findByMemberId를 통해서 memberId로 developer Entity를 가져오고 
        // 가져온 entity를 맵 함수를 통해 fromEntity 메소드를 통해가지고 
        // developer Entity를 DeveloperDetailDto로 변환해주고 
        // DeveloperDetailDto를 변환해주는데 developer Entity를 못 가져왔을 때 예외처리
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(()-> new DMakerException(NO_DEVELOPER));
                // 값이 있으면 값 자체를 get 해서 받아 넘겨주고 
                // 값이 없으면 특정 동작을 수행해주게 됨
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(
            String memberId, EditDeveloper.Request request
    ) {
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(
                        () -> new DMakerException(NO_DEVELOPER)
                );
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());
        developer.setName(request.getName());
        developer.setAge(request.getAge());

        return DeveloperDetailDto.fromEntity(developer);
    }

    // 단 한번의 db 조작이 있더라도 @Transactional 어노테이션 넣어주기
    // -> Dirty Checking을 통한 자동 데이터 업데이트도 이 트랜잭션 어노테이션을 통해 이루어짐
    // 추후 로직 변화 가능성을 열어두고 트랜잭션을 붙여주는 게 유리함
    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        
        // 1. EMPLOYED 상태인 개발자를 RETIRED로 변경
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(()-> new DMakerException(NO_DEVELOPER));
        // 삭제할 developer의 정보를 가져오면서 간단한 Validation 체크까지 수행

        developer.setStatus(StatusCode.RETIRED);

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();
        // RetiredDeveloper의 엔티티를 생성


        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);

    }
}
