package org.ms.announcer.repositories;

import java.util.Optional;

import org.ms.announcer.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
/**
 * MemberRepository
 */
public interface MemberRepository extends JpaRepository<MemberVO,Integer>{
<<<<<<< HEAD
    Optional<MemberVO> findBymemberid(String memberid);
=======
   
    public Optional<MemberVO> findById(String id);

    @Modifying
    @Query(value = "update CPInfo cp set cp.title = :title, cp.introduce =:introduce where cp.member = :member")
    public int updateCPInfo(String title, String introduce, MemberVO member);


    // @Query(value = "update MemberVO m set m.cpInfo.title = :title where memberid = :memberid")
    // int updateCPInfo(String title, String memberid);
    
>>>>>>> 362be0647d4773cc70f49220055ac57345730ddf
    
}