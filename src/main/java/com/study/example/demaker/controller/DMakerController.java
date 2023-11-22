package com.study.example.demaker.controller;

import com.study.example.demaker.dto.CreateDeveloper;
import com.study.example.demaker.dto.DeveloperDetailDto;
import com.study.example.demaker.dto.DeveloperDto;
import com.study.example.demaker.dto.EditDeveloper;
import com.study.example.demaker.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    // dmakerService를 자동으로 injection
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllEmployedDevelopers() {
        // GET / developers HTTP/1.1
        log.info("GET / developers HTTP/1.1");
        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        // GET / developers HTTP/1.1
        log.info("GET / developers HTTP/1.1");
        return dMakerService.DeveloperDetailDto(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDeveloper(
            @Valid @RequestBody CreateDeveloper.Request request) {

        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);

    }

    @PutMapping("/edit-developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request) {

        return dMakerService.editDeveloper(memberId, request);

    }


    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId) {

        return dMakerService.deleteDeveloper(memberId);

    }
}
