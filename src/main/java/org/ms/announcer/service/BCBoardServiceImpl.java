package org.ms.announcer.service;


import java.time.LocalDate;

import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.repositories.BCBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * BCBoardServiceImpl
 */
@Service
@Slf4j
public class BCBoardServiceImpl implements BCBoardService {

    @Setter(onMethod_ = { @Autowired })
    private BCBoardRepository repos;

    @Override
    public void register(BCBoardDTO dto) {
        repos.save(dto);
    }

    @Override
    public Page<BCBoardDTO>getlist(Pageable page) {
        Page<BCBoardDTO> result = repos.findAll(page);
        log.info("==========================");
        System.out.println(page.toString());
        log.info("service: "+result);
        return result;
    }

    public Page<BCBoardDTO> getTodayList( LocalDate date, Pageable page) {
        Page<BCBoardDTO> result = repos.findAllByStartdate(date, page);
        return result;
    }

    

    
}