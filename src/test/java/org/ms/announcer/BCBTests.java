package org.ms.announcer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.ms.announcer.domain.BCBoardDTO;
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
    @Test
    public void Insert() {
        IntStream.range(1, 10).forEach(i ->{
            
            BCBoardDTO dto = new BCBoardDTO();
            dto.setTitle("제목입니다"+i);
            dto.setContent("내용입니다"+i);
            dto.setAudioName("audioName"+i);

            // LocalDate sdate = LocalDate.of(2019, (i%11==0 ? 1 : i%11), ((i*3)%30)==0? 1: i*3%30);
            LocalDate sdate = LocalDate.of(2019,12,10);
            dto.setStartdate(sdate);

            // LocalTime stime = LocalTime.of((i*3)%23 == 0 ? 1: (i*3)%23 , (i*7)%59 ==0  ? 1: (i*7)%59  );
            LocalTime stime = LocalTime.of(10 , (i)%59 == 0 ? 1: (i)%59  );
            dto.setStarttime(stime);
            dto.setAudioName("C:-AudioStorage-tmp-697962978bbc49dda42f3f9c95a942f5_tmp긴급공지테스트중입니다.wav");
            service.register(dto);

        });
    }

    
}