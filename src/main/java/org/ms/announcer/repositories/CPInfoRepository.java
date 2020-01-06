package org.ms.announcer.repositories;

import org.ms.announcer.domain.CPInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CPInfoRepository extends JpaRepository<CPInfo, Integer> {

    // public Page<CPInfo> getAllCPFindBytitle(String title, Pageable page);
    // @Query(value = "Select * from CPInfo b")
    // public Page<CPInfo> findByTitleLike( Pageable page);

}