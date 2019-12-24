package org.ms.announcer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ms.announcer.domain.MemberRole;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MemberController
 */

@RestController
@RequestMapping("/member/*")
public class MemberController {

    @Autowired
    private PasswordEncoder pe;
    
    @Autowired
    MemberRepository memberRepository;

    @PostMapping("register")
    public void register( @RequestBody MemberVO vo){
        System.out.println("-------------------------------");
        System.out.println(vo.getMemberid());
        System.out.println(vo.getMemberpassword());
        System.out.println(vo.getName());
        vo.setMemberpassword(pe.encode(vo.getMemberpassword()));
        MemberRole a = new MemberRole();
        a.setRoleName("ROLE_BASIC");
        List<MemberRole> rList = new ArrayList<>();
        rList.add(a);
        vo.setRoles(rList);
        memberRepository.save(vo);
    }

    @GetMapping("checkOverOverlap/{memberid}")
    public boolean checkOverOverlap(@PathVariable("memberid") String memberid){
        System.out.println(memberid);
        Optional<MemberVO> member = memberRepository.findByMemberid(memberid);
        if(member.isPresent()){
            return true;
        }else{
            return false;
    }
        
    }
}