package org.ms.announcer.service;

import java.util.Map;

import org.ms.announcer.domain.BCBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * BCBoardService
 */
public interface BCBoardService {

    public void register(BCBoardDTO dto);
    public BCBoardDTO read(Integer bno);
    public void update(BCBoardDTO dto);
    public void delete(Integer bno);
    public Map<String , Object> getAllList(Pageable page, String category, String search, String userName);
    public Page<BCBoardDTO> getTodayList(String date , String week, String mid, Pageable page );
    
}