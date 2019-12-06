package org.ms.announcer.service;

import java.util.List;

import org.ms.announcer.domain.BCBoardDTO;

/**
 * BCBoardService
 */
public interface BCBoardService {

    public void register(BCBoardDTO dto);

    public List<BCBoardDTO> getlist();
    
}