package org.ms.announcer.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.ms.announcer.domain.AudioVO;
import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.domain.RepeatVO;
import org.ms.announcer.repositories.BCBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.Setter;

/**
 * BCBoardServiceImpl
 */
@Service
public class BCBoardServiceImpl implements BCBoardService {

    @Setter(onMethod_ = { @Autowired })
    private BCBoardRepository repos;

    @Override
    public void register(BCBoardDTO dto) {
        repos.save(dto);
    }
    
    @Override
    public void update(BCBoardDTO dto) {
        BCBoardDTO target = read(dto.getBno());
        target.setTitle(dto.getTitle());
        target.setContent(dto.getContent());
        target.setGender(dto.getGender());
        target.setStartdate(dto.getStartdate());
        target.setStarttime(dto.getStarttime());
        AudioVO targetAudio = dto.getAudioVO();
        target.setAudioVO(targetAudio);
        RepeatVO targetRepeat = dto.getRepeatVO();
        target.setRepeatVO(targetRepeat);
        repos.save(target);
    }

    @Override
    public BCBoardDTO read(Integer bno) {
        return repos.findById(bno).orElse(null);  // = rpose.findById(bno).get();
    }

    @Override
    public void delete(Integer bno) {
        repos.deleteById(bno);
    }

    public Page<BCBoardDTO> getTodayList(String date, String week, String mid,  Pageable page) {
        // Page<BCBoardDTO> result = repos.findAllByStartdate(date, page);
        week = "%"+week+"%";
        String monthRepeat ="%" +(date.split("-")[2])+ "%";
        Page<BCBoardDTO> result = repos.findByStartdate(date, week, monthRepeat, mid , page);
        return result;
    }

    @Override
    public Map<String, Object> getAllList(Pageable page, String category, String search, String userName) {
        Page<BCBoardDTO> data = null;
        search = "%" + search + "%";

        // 카테고리구분
        if (category.equals("title")) {
            data = repos.findByTitleLike(search, page, userName );
        } else if(category.equals("year-month-day")){
            String[] searchDateArr = search.replace("%", "").split("-");
            LocalDate sdate = LocalDate.of(Integer.parseInt(searchDateArr[0]),Integer.parseInt(searchDateArr[1]),Integer.parseInt(searchDateArr[2]));
            data = repos.findYMDTotall(sdate, page, userName);
        } else {
            String[] searchDateArr = search.replace("%", "").split("-");
            LocalDate sdate = LocalDate.of(Integer.parseInt(searchDateArr[0]),Integer.parseInt(searchDateArr[1]),1);
            YearMonth temp = YearMonth.of(sdate.getYear(), sdate.getMonth());
            data = repos.findByStartdateBetween(sdate, temp.atEndOfMonth(),page, userName );
        }
        Map<String, Object> result = new HashMap<>();
        int now = data.getNumber();
        int total = data.getTotalPages();

        // 시작페이지 설정
        int start = 0;
        if (now - 1 <= 0 || total <= 5) {
            start = 1;
        } else {
            start = (now + 1) - 2;
        }
        
        // 마지막페이지 설정
        int end = 0;
        if (total <= 5 || now + 3 > total) {
            end = total;
        } else if (total > 5 && (now + 1) < 3) {
            end = 5;
        } else {
            end = now + 3;
        }
        //같거나 마이너스 1이면 시작은 
        
        // 리스트 데이터
        result.put("start", start);
        result.put("end", end);
        result.put("data", data.getContent());
        result.put("now", data.getNumber() + 1);
        result.put("totalpages", total);
        result.put("category", category);
        result.put("search", search.replace("%", ""));
        System.out.println(result.get("now"));
        System.out.println(result.get("end"));
        return result;
    }

    
}