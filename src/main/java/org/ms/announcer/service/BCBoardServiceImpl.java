package org.ms.announcer.service;

import java.util.List;

import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.repositories.BCBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BCBoardDTO> getlist() {
        List<BCBoardDTO> result = repos.findAll();
        log.info("==========================");
        log.info("service: "+result);
        return result;
    }

    

    
}