package org.ms.announcer.controller;

import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MemberService ms;

    @PostMapping("register")
    public void register( @RequestBody MemberVO vo){
            System.out.println(vo.getType());
        ms.RegistMemeber(vo);
    }

    @GetMapping("checkOverlap/{memberid}")
    public boolean checkOverlap(@PathVariable("memberid") String memberid){
     return ms.checkOvaelap(memberid);
    }
}