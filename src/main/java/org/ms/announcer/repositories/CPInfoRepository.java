package org.ms.announcer.repositories;

import org.ms.announcer.domain.CPInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CPInfoRepository extends JpaRepository<CPInfo, Integer> {

    // public Page<CPInfo> getAllCPFindBytitle(String title, Pageable page);
    // @Query(value = "Select * from CPInfo b")
    // public Page<CPInfo> findByTitleLike( Pageable page);

}