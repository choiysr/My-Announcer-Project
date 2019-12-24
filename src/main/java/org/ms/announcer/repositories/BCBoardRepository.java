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

    // @Query("SELECT * FROM BCBoardDTO b where b.title like (:title) and b.mid = :userName ")
    @Query(value = "SELECT * FROM tbl_bcboard b where b.mid = ?2 and  b.title like (?1)", nativeQuery = true)
    Page<BCBoardDTO> findByTitleLike(String title, Pageable page, String userName );
    
    @Query(value = "SELECT * FROM tbl_bcboard b where (b.startdate BETWEEN ?1 and ?2 ) and b.mid = ?3", nativeQuery = true)
    Page<BCBoardDTO> findByStartdateBetween(LocalDate startdate, LocalDate end, Pageable page, String userName);
    //todayList
    Page<BCBoardDTO> findAllByStartdate(LocalDate date, Pageable page, String userName);
    //totalList
    // @Query(value = "", nativeQuery = true)
    // Page<BCBoardDTO> findByStartdate( Pageable page);
    
    @Query(value = "SELECT * FROM tbl_bcboard b where b.startdate = ?1 and  b.mid = ?2", nativeQuery = true)
    Page<BCBoardDTO> findYMDTotall(LocalDate startdate, Pageable page, String userName);
    
    
    @Query( value = "select b.* from tbl_bcboard b where (DATE_FORMAT(b.startdate, '%Y-%m-%d') = ?1 or repeat_week like (?2) or repeat_month like (?3)) and mid = ?4 ", nativeQuery = true)
    Page<BCBoardDTO> findByStartdate(String startDate, String week, String month, String mid, Pageable page);
}