package org.ms.announcer.repositories;

import java.util.Optional;

import org.ms.announcer.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * MemberRepository
 */
public interface MemberRepository extends JpaRepository<MemberVO,Integer>{
    Optional<MemberVO> findBymemberid(String memberid);
    
}