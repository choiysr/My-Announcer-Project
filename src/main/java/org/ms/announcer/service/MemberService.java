package org.ms.announcer.service;

import org.ms.announcer.domain.MemberVO;
import org.springframework.data.domain.Page;

/**
 * MemberService
 */
public interface MemberService {
    public void RegistMemeber(MemberVO vo);
    public boolean checkOvaelap(String memberid);
    public void updateCPinfo(MemberVO vo);
    public Page<MemberVO> getAllCPByTilte(String title, int currentPage);
}