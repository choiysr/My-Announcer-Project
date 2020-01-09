package org.ms.announcer.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.ms.announcer.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * AdminRepository
 */
public interface AdminRepository extends JpaRepository<LoginHistory, Integer> {

 @Query(value = 
 "select function('date_format',h.loginDate,'%d') as dates,count(h.memberId) as counts "
 +"from LoginHistory h "
 +"where function('date_format',h.loginDate,'%Y-%m') = function('date_format',:today,'%Y-%m') "
 +"group by h.loginDate")
  public List<Map<String, Integer>> getCountUserbyDayOfThisMonth(@Param("today") LocalDate today);  
  
}