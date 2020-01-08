package org.ms.announcer.repositories;

import java.util.Optional;

import org.ms.announcer.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * MemberRepository
 */
public interface MemberRepository extends JpaRepository<MemberVO, Integer> {

    public Optional<MemberVO> findById(String id);

    @Modifying
    @Query(value = "update CPInfo cp set cp.title = :title, cp.imgFile = :imgFile, cp.introduce =:introduce where cp.member = :member")
    public int updateCPInfo(String title, String introduce, String imgFile, MemberVO member);
    



}