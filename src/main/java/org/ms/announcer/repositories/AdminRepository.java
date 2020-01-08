package org.ms.announcer.repositories;

import java.time.LocalDate;

import org.ms.announcer.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * AdminRepository
 */
public interface AdminRepository  extends JpaRepository<LoginHistory, Integer>{

    @Query(value = "select * from tbl_login_history where login_date = :date and memeber_id = :userName", nativeQuery = true)
    public LoginHistory findHistory(LocalDate date, String userName);

    @Query(value = "select count(*) from tbl_login_history where login_date = :date", nativeQuery = true)
    public Integer findCountOfLoginByDate (LocalDate date);

    
}