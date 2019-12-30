package org.ms.announcer;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.domain.Subscribe;
import org.ms.announcer.repositories.BCBoardRepository;
import org.ms.announcer.repositories.MemberRepository;
import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SubscribeTests
 */
@SpringBootTest
public class SubscribeTests {

    @Autowired
    MemberRepository mr;
    
    @Autowired
    BCBoardService service;

    @Autowired
    BCBoardRepository repo;

    @Test
    @Transactional
    public void addSubscribe() {
       MemberVO bc = mr.findById("bctest").get();
       MemberVO cp = mr.findById("cptest").get();
        Subscribe e = new Subscribe();
        e.setCp(cp);
        e.setMember(bc);
       
       bc.getSubscribes().add(e);
        

    }
    
}