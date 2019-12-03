package org.ms.announcer.repositories;

import org.ms.announcer.domain.BCBoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BCBoardRepository
 */
public interface BCBoardRepository extends JpaRepository<BCBoardDTO,Integer>{
    
}