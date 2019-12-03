package org.ms.announcer.service;

import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.repositories.BCBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

/**
 * BCBoardServiceImpl
 */
@Service
public class BCBoardServiceImpl implements BCBoardService {

    
    @Setter(onMethod_ = {@Autowired} )
    private BCBoardRepository repos;


    @Override
    public void register(BCBoardDTO dto) {
        repos.save(dto);
    }

    
}