package com.study.example.demaker.repository;

import com.study.example.demaker.code.StatusCode;
import com.study.example.demaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository 어노테이션이 없어도 됨, 왜 없어도 되는지는 찾아보기
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByMemberId(String memberId);

    List<Developer> findDevelopersByStatusEquals(StatusCode statusCode);
}
