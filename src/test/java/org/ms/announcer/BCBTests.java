package org.ms.announcer;

import java.time.LocalTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.AudioVO;
import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.domain.RepeatVO;
import org.ms.announcer.repositories.BCBoardRepository;
import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * BCBTests
 */
@SpringBootTest
public class BCBTests {

    @Autowired
    BCBoardService service;

    @Autowired
    BCBoardRepository repo;

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
            // dto.setRepleRepeatVO(repeatVO);
            repeatVO.setRepeatMonth("16");
            dto.setRepleRepeatVO(repeatVO);

            dto.setAudioVO(audio);
            service.register(dto);

        });
    }

}