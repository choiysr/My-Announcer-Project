package org.ms.announcer.repositories;

import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CPBoardRepository
 */
public interface CPBoardRepository extends JpaRepository<CPBoard, Integer>{

    public List<CPBoard> findByMemberID(MemberVO member);
    
}