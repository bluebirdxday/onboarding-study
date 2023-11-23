package com.study.example.demaker.service;

import com.study.example.demaker.code.StatusCode;
import com.study.example.demaker.dto.CreateDeveloper;
import com.study.example.demaker.dto.DeveloperDetailDto;
import com.study.example.demaker.entity.Developer;
import com.study.example.demaker.exception.DMakerErrorCode;
import com.study.example.demaker.exception.DMakerException;
import com.study.example.demaker.repository.DeveloperRepository;
import com.study.example.demaker.repository.RetiredDeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.study.example.demaker.type.DeveloperLevel.SENIOR;
import static com.study.example.demaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    //@Autowired
    @InjectMocks
    private DMakerService dMakerService;

    private final Developer defaultDeveloper =
            Developer.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .status(StatusCode.EMPLOYED)
            .name("name")
            .age(12)
            .build();

    private final CreateDeveloper.Request defaultCreateRequest =
            CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();

    @Test
    public void testSomething(){
        // mock들의 동작을 정의
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));


        DeveloperDetailDto developerDetailDto = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetailDto.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetailDto.getDeveloperSkillType());
        assertEquals(12, developerDetailDto.getExperienceYears());

    }



    @Test
    void createDeveloperTest_success(){
        // 라이브 템플릿 - 코멘트를 테스트에 구조화로 활용
        // given - 모킹이라던가 테스트에 활용된 지역 변수 만들어놓기


        // 예외가 발생할 수 있는 부분 모킹
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        given(developerRepository.save(any()))
                .willReturn(defaultDeveloper);

        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        // when - 테스트하고자 하는 동작, 동작으로 인해 만들어진 결과 값을 받아오는 부분
        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);

        // then - assertion / verify를 통해 예상대로 동작하는지 검증
        verify(developerRepository, times(1))
                .save(captor.capture());

        Developer saveDeveloper = captor.getValue();
        assertEquals(SENIOR, saveDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, saveDeveloper.getDeveloperSkillType());
        assertEquals(12, saveDeveloper.getExperienceYears());
    }


    @Test
    void createDeveloperTest_failed_with_duplicated(){
        // 라이브 템플릿 - 코멘트를 테스트에 구조화로 활용
        // given - 모킹이라던가 테스트에 활용된 지역 변수 만들어놓기


        // 예외가 발생할 수 있는 부분 모킹
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        // when - 테스트하고자 하는 동작, 동작으로 인해 만들어진 결과 값을 받아오는 부분
        // then - assertion / verify를 통해 예상대로 동작하는지 검증
       DMakerException dMakerException =  assertThrows(DMakerException.class,
               ()-> dMakerService.createDeveloper(defaultCreateRequest));

       // assertEquals(DMakerErrorCode.NO_DEVELOPER, dMakerException.getDMakerErrorCode()); -> ERROR
        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());


    }
}