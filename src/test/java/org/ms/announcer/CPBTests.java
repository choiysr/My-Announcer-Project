package org.ms.announcer;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.CPInfo;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.repositories.CPInfoRepository;
import org.ms.announcer.service.CPBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import lombok.Setter;

/**
 * CPBTests
 */
@SpringBootTest
public class CPBTests {

    @Setter(onMethod_ = { @Autowired })
    private CPBoardService service;
    
    @Setter(onMethod_ = { @Autowired })
    private CPInfoRepository cr;


    @Test
    @Transactional
    public void getCPListTest() {

        MemberVO member = new MemberVO();
        member.setId("aaaaa");
        List<CPBoard> result = new ArrayList<>();
        result = service.getCPBoardList(member);

    }
    @Test
    public void cpInfoTest() {

        // String title = "%%";
        // Pageable page = PageRequest.of(0, 10, Direction.ASC, "Title");
        List<CPInfo> list =    cr.findAll();
        System.out.println(list);

    }

    
}