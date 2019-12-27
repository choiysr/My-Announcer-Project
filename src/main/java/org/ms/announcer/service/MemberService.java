package org.ms.announcer.service;

import org.ms.announcer.domain.MemberVO;

/**
 * MemberService
 */
public interface MemberService {
    public void RegistMemeber(MemberVO vo);

    public boolean checkOvaelap(String memberid);

    public void updateCPinfo(MemberVO vo);
}