package org.ms.announcer.service;

import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;

public interface CPBoardService {

    public void register(CPBoard cpboard);
    public void update(CPBoard cpboard);
    public MemberVO getCP(String id); // 만기야 이거 메서드 이름 너무 직관적이지 않음. 변경요망
    public List<CPBoard> getCPBoardList(MemberVO member);
    public CPBoard getOneCPBoard(Integer bno);
    
}