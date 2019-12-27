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
import org.springframework.transaction.annotation.Transactional;

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
            CPInfo info = new CPInfo();
            info.setMember(vo);
            vo.setCpInfo(info);
            
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
        Optional<MemberVO> member = memberRepository.findById(memberid);
        if (member.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void updateCPinfo(MemberVO vo1) {
        MemberVO vo2 = memberRepository.findById(vo1.getId()).get();

        memberRepository.updateCPInfo(vo1.getCpInfo().getTitle(), vo1.getCpInfo().getIntroduce(),vo2);
    }

    
}