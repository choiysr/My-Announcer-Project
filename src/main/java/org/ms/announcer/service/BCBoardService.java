package org.ms.announcer.service;

import java.time.LocalDate;

import org.ms.announcer.domain.BCBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * BCBoardService
 */
public interface BCBoardService {

    public void register(BCBoardDTO dto);

    public Page<BCBoardDTO> getlist(Pageable page);

    public Page<BCBoardDTO> getTodayList(LocalDate date , Pageable page );
    
}