package org.ms.announcer.service;

import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;

public interface CPBoardService {

    public void register(CPBoard cpboard);
    public List<CPBoard> getList(MemberVO member);
    
}