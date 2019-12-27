package org.ms.announcer.service;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;

public interface CPBoardService {

    public void register(CPBoard cpboard);
    public MemberVO getCP(String userName);
    
}