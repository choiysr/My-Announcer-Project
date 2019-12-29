package org.ms.announcer;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.service.CPBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Setter;

/**
 * CPBTests
 */
@SpringBootTest
public class CPBTests {

    @Setter(onMethod_ = { @Autowired })
    private CPBoardService service;


    @Test
    @Transactional
    public void getCPListTest() {

        MemberVO member = new MemberVO();
        member.setId("aaaaa");
        List<CPBoard> result = new ArrayList<>();
        result = service.getCPBoardList(member);

    }

    
}