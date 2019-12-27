package org.ms.announcer.service;

import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.repositories.CPBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

@Service
public class CPBoardServiceImpl implements CPBoardService {
    @Setter(onMethod_ = { @Autowired })
    private CPBoardRepository cprepo;

    @Override
    public void register(CPBoard cpboard) {
        cprepo.save(cpboard);
    }

    @Override
    public List<CPBoard> getList(MemberVO member) {
        return null;
    }

  
    
}