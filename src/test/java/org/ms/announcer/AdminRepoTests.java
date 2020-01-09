package org.ms.announcer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.LoginHistory;
import org.ms.announcer.repositories.AdminRepository;
import org.ms.announcer.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Setter;

/**
 * AdminRepoTests
 */

@SpringBootTest
public class AdminRepoTests {

    @Setter(onMethod_ = { @Autowired })
    AdminRepository arepo;

    @Setter(onMethod_ = { @Autowired })
    MemberRepository mrepo;


    @Test
    public void inputDummies() {

      /*   IntStream.range(96,101).forEach(i -> {
            MemberVO member = new MemberVO();
            member.setId("testuser"+i);
            member.setAddress("gangnamgu");
            member.setName("mynameisuser"+i);
            member.setMemberpassword("1111");
            LocalDateTime date = LocalDateTime.of(LocalDate.of(2020, 1, 7),LocalTime.of(0,1,1));
            member.setEmail("aaaa@naver.com");
            member.setRegdate(date);
            mrepo.save(member);
        }); */
     
         IntStream.range(1, 8).forEach(day ->  {
            LocalDate today = LocalDate.of(2020, 1, day);
            int randomUser = (int)(Math.random()*99)+1;
            for(int i=1;i<=randomUser; i++) {
                LoginHistory history = new LoginHistory();
                history.setLoginDate(today);
                history.setMemberId("testuser"+i);
                arepo.save(history);
            }
        }); 
    }

    @Test
    public void testQuery() {

    LocalDate today = LocalDate.now();
    System.out.println("================");
    System.out.println(today);
    List<Map<String,Integer>> result = arepo.getCountUserbyDayOfThisMonth(today); 
    System.out.println(" == ");

    }

}