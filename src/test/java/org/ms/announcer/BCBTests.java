package org.ms.announcer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.AudioVO;
import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.domain.CPInfo;
import org.ms.announcer.domain.MemberRole;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.domain.RepeatVO;
import org.ms.announcer.repositories.AdminRepository;
import org.ms.announcer.repositories.BCBoardRepository;
import org.ms.announcer.repositories.MemberRepository;
import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


/**
 * BCBTests
 */
@SpringBootTest
public class BCBTests {

    @Autowired
    MemberRepository mr;
    
    @Autowired
    BCBoardService service;

    @Autowired
    BCBoardRepository repo;
    
    @Autowired
    AdminRepository ap;
    

    @Autowired
    private PasswordEncoder pe;

    @Test
    public void Insert() {
        IntStream.range(1, 3).forEach(i -> {
            BCBoardDTO dto = new BCBoardDTO();
            dto.setTitle("제목입니다" + i);
            dto.setContent("내용입니다" + i);
            // dto.setAudioName("audioName"+i);

            // LocalDate sdate = LocalDate.of(2019, (i%11==0 ? 1 : i%11), ((i*3)%30)==0? 1:
            // i*3%30);
            // LocalDate sdate = LocalDate.of(2019, 12, ((i*3)%30)==0? 1: i*3%30);
            // LocalDate sdate = LocalDate.of(2019, 12, 16);
            // dto.setStartdate(sdate);

            LocalTime stime = LocalTime.of((i * 4) % 23 == 0 ? 1 : (i * 5) % 23, (i * 6) % 59 == 0 ? 1 : (i * 7) % 59);
            // LocalTime stime = LocalTime.of(12 , (i)%59 == 0 ? 1: (i)%59 );
            dto.setStarttime(stime);
            dto.setGender("woman");

            AudioVO audio = new AudioVO();
            audio.setAudioPath("C:\\AudioStorage\\19\\12\\12\\");
            audio.setAudioName("b6c31a4faf7d44c299e960aea131e5f0_fdfdf.wav");
            audio.setAlarmBell("1");
            ;

            // 주간반복
            RepeatVO repeatVO = new RepeatVO();
            // repeatVO.setRepeatWeek("0,1,4");
            // dto.setrepeatVO(repeatVO);
            repeatVO.setRepeatMonth("16");
            // dto.setrepeatVO(repeatVO);

            dto.setAudioVO(audio);
            service.register(dto);

        });
    }

    @Test
    public void cpMemberRegist(){
        MemberVO vo = new MemberVO();

        vo.setId("memberid22");
        vo.setMemberpassword("12345");
        vo.setType("CP");
        CPInfo cpinfo = new CPInfo();
        cpinfo.setIntroduce("introduce2222222222");
        vo.setCpInfo(cpinfo);
        vo.setMemberpassword(pe.encode(vo.getMemberpassword()));
        MemberRole a = new MemberRole();
        a.setRoleName("ROLE_BASIC");
        List<MemberRole> rList = new ArrayList<>();
        rList.add(a);
        vo.setRoles(rList);
        vo.setType("user");
        mr.save(vo);
    }


    @Test
    public void selectMemeber(){
    //    Optional<MemberVO> a= mr.findByMemberid("memberid22");
    //    System.out.println(a.isPresent());
    //    System.out.println(a.get().getCpInfo().getIntroduce());
    }
    @Test

    @Commit
    @Transactional 
    public void updateTests(){
        // CPInfo info = new CPInfo();
        // info.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaS");
        // info.setIntroduce("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaS");

        // MemberVO member = mr.findByMemberid("aaaaa").get();

        // mr.updateCPInfo("cptest3333","EEEEEEEEEEEEEEEEEE" , member);
    //   mr.updateCPInfo("tilte", "cptest");
    }

    @Test
    public void countTest(){

        // 오늘 접속하 사람의 수 
        System.out.println(ap.findCountOfLoginByDate(LocalDate.now()));

        //
        System.out.println(mr.findRegisterdCount(LocalDate.of(2019, 12, 30).plusDays(1)));

    }

}