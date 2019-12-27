package org.ms.announcer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ms.announcer.domain.CPInfo;
import org.ms.announcer.domain.MemberRole;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * MemberServiceImpl
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private PasswordEncoder pe;
    
    @Autowired
    MemberRepository memberRepository;

    @Override
    public void RegistMemeber(MemberVO vo) {
        MemberRole a = new MemberRole();

        if(vo.getType().equals("CP")){
            a.setRoleName("ROLE_CP");
            vo.setCpInfo(new CPInfo());
        }else{
            vo.setType("user");
            a.setRoleName("ROLE_USER");
        }
        vo.setMemberpassword(pe.encode(vo.getMemberpassword()));
        List<MemberRole> rList = new ArrayList<>();
        rList.add(a);
        vo.setRoles(rList);
        memberRepository.save(vo);
    }

    @Override
    public boolean checkOvaelap(String memberid) {
        Optional<MemberVO> member = memberRepository.findByMemberid(memberid);
        if (member.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    
}