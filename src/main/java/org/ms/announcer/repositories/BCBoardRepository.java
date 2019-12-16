package org.ms.announcer.repositories;

import java.time.LocalDate;

import org.ms.announcer.domain.BCBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * BCBoardRepository
 */
public interface BCBoardRepository extends JpaRepository<BCBoardDTO, Integer> {

    Page<BCBoardDTO> findByTitleLike(String title, Pageable page);
    
    Page<BCBoardDTO> findByStartdateBetween(LocalDate startdate, LocalDate end, Pageable page);
    //todayList
    Page<BCBoardDTO> findAllByStartdate(LocalDate date, Pageable page);
    //totalList
    // @Query(value = "", nativeQuery = true)
    // Page<BCBoardDTO> findByStartdate( Pageable page);

    Page<BCBoardDTO> findByStartdate(LocalDate startdate, Pageable page);
    
    
    @Query( value = "select b.* from tbl_bcboard b where DATE_FORMAT(b.startdate, '%Y-%m-%d') = ?1 or repeat_week like (?2) or repeat_month like (?3) ", nativeQuery = true)
    Page<BCBoardDTO> findByStartdate(String startDate, String week, String month, Pageable page);
}