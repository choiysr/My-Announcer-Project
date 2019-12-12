package org.ms.announcer.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.service.BCBoardService;
import static org.ms.announcer.utils.FileUtil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.Setter;

/**
 * BCBoardController
 */
// 오늘목표 : 글 받아서 DB에 등록하고 audio파일 저장확인. 미리듣기 설정(글 바꿀때마다 기존꺼 날리고).
// list 페이지에서 즉시재생시키기(1번만)

@RestController
@RequestMapping("/rbcboard/*")
@CrossOrigin
public class BCBoardController {

    @Setter(onMethod_ = { @Autowired })
    private BCBoardService service;

    // ==================== Intro/ending 파일업로드 ===================
    @GetMapping(value = "/fileUpload")
    public ResponseEntity<List<String>> fileUpload(MultipartFile additionalAudio) {
        System.out.println("controller진입확인");
        // 유효성검사는 view단에서 완료
        // 경로만 hidden으로 리턴해줘서 나중에 prelisten누르면 같이 보내줄것임. (prelisten수정 view and controller)
        List<String> list = new ArrayList<>();
        try {
            list.add(audioSave("tmp" + additionalAudio.getOriginalFilename(), additionalAudio.getBytes()).replace("\\", "/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(list, OK);
    }

    // ==================== 미리듣기 ====================
    // 등록하는거 아니면 모두 get으로 받아라.. mapping성격이 다 다르다
    @GetMapping(value = "/prelisten")
    public ResponseEntity<List<String>> preListen(BCBoardDTO dto) {
        ResponseEntity<byte[]> response = makeAudio(dto);
        List<String> list = new ArrayList<>();
        list.add(audioSave("tmp" + dto.getTitle(), response.getBody()).replace("\\", "-"));
        return new ResponseEntity<>(list, OK);
    }

    @GetMapping(value = "/{uploadPath}")
    public ResponseEntity<byte[]> pathCheck(@PathVariable("uploadPath") String uploadPath) {
        System.out.println(uploadPath);
        File audioFile = new File(uploadPath.replace("-", "\\"));
        byte[] audioData = null;
        try {
            audioData = FileCopyUtils.copyToByteArray(audioFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(audioData, OK);
    }

    // ==================== End 미리듣기 ====================

    // 최종 저장
    @PostMapping(value = "/register")
    public void registerBC(@RequestBody BCBoardDTO dto) {
        System.out.println("컨트롤러에서 dto잘 들어왔나 확인해 봅시다======");
        System.out.println(dto);
        System.out.println(dto.getStartdate());
        System.out.println(dto.getStarttime());

        ResponseEntity<byte[]> response = makeAudio(dto);
        String wholePath = audioSave(dto.getTitle(), response.getBody());
        String pathWithoutFname = wholePath.substring(0, wholePath.lastIndexOf("\\") + 1); // 파일명을 제외한 경로.뒤에 슬래시 포함
        String fileName = wholePath.substring((pathWithoutFname.length()), wholePath.length()); // uuid+파일명
        dto.getAudioVO().setAudioPath(pathWithoutFname);
        dto.getAudioVO().setAudioName(fileName);
        service.register(dto);
    }

    // get 방식으로 url dptj 원하는 페이지, 오늘 날짜를 받는다.
    @GetMapping("/todayList/{startdate}")
    public ResponseEntity<Page<BCBoardDTO>> list(@PathVariable("startdate") String startdate) {
        // 정렬하여 값 가져올 기준
        Pageable page = PageRequest.of(0, 300, Direction.ASC, "startdate", "starttime");
        // 문자열로 가저온 것을 split
        String[] date = startdate.split("-");
        // 값을 얻어온다.
        Page<BCBoardDTO> result = service.getTodayList(
                LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])), page);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/totalList")
    public ResponseEntity<Map<String, Object>> getTotalList(
            @PageableDefault(page = 0, direction = Direction.ASC, sort = { "startdate", "starttime" }) Pageable page,
            String category, String search) {
        Map<String, Object> result = service.getAllList(page, category, search);
        return new ResponseEntity<>(result, OK);
    }

    ////////// API METHOD ////////////

    public ResponseEntity<byte[]> makeAudio(BCBoardDTO dto) {

        String data = "";
        String url = "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("charset", "UTF-8");

        // 네이버 API : 남자 음성 사용
        String clovaUrl = "https://naveropenapi.apigw.ntruss.com/voice/v1/tts";
        String keyID = "8un3nj2jlx";
        String secretKey = "T1ZHOULzmPIOa58HmHvByTgGnMDTekgF8oZ1XSaE";

        // 카카오 API 정보 : 여자 음성 사용
        String kakaoUrl = "https://kakaoi-newtone-openapi.kakao.com/v1/synthesize";
        String authKey = "KakaoAK 51b790b9e32597330a3825d667ef2c35";

        if (dto.getGender().equals("man")) {
            // 남자 음성 사용
            data = "speaker=jinho&speed=0&text=" + dto.getContent();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("X-NCP-APIGW-API-KEY-ID", keyID);
            headers.add("X-NCP-APIGW-API-KEY", secretKey);
            url = clovaUrl;
        } else {
            // 여자 음성 사용
            data = "<speak><voice name=\"WOMAN_READ_CALM\">" + dto.getContent() + "</voice></speak>";
            headers.add("Content-Type", "application/xml");
            headers.add("Authorization", authKey);
            url = kakaoUrl;
        }
        return restTemplate.postForEntity(url, new HttpEntity<byte[]>(data.getBytes(), headers), byte[].class);
    }

    // KAKAO API
    /*
     * public ResponseEntity<byte[]> makeAudio(BCBoardDTO dto) {
     * 
     * String kakaoUrl = "https://kakaoi-newtone-openapi.kakao.com/v1/synthesize";
     * String authKey = "KakaoAK 51b790b9e32597330a3825d667ef2c35"; String xmlString
     * = "<speak>"; final String womanVoice = "<voice name=\"WOMAN_READ_CALM\">";
     * final String manVoice = "<voice name=\"MAN_READ_CALM\">"; RestTemplate
     * restTemplate = new RestTemplate();
     * 
     * xmlString += dto.getGender().equals("man") ? manVoice : womanVoice; xmlString
     * += dto.getContent() + "</voice></speak>";
     * 
     * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
     * "application/xml"); headers.add("Authorization", authKey);
     * headers.add("charset", "UTF-8"); return restTemplate.postForEntity(kakaoUrl,
     * new HttpEntity<byte[]>(xmlString.getBytes(), headers), byte[].class); }
     * 
     * // NAVER API public ResponseEntity<byte[]> makeAudio2(BCBoardDTO dto) {
     * 
     * // -H "Content-Type:application/x-www-form-urlencoded" \ // 'speaker={목소리
     * 종류}&speed={음성 재생 속도}&text={텍스트}' \ String clovaUrl =
     * "https://naveropenapi.apigw.ntruss.com/voice/v1/tts"; String keyID =
     * "8un3nj2jlx"; String secretKey = "T1ZHOULzmPIOa58HmHvByTgGnMDTekgF8oZ1XSaE";
     * String speakerGender = dto.getGender().equals("man") ? "jinho" : "mijin";
     * String data = "speaker=" + speakerGender + "&speed=0&text=" +
     * dto.getContent();
     * 
     * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
     * "application/x-www-form-urlencoded"); headers.add("X-NCP-APIGW-API-KEY-ID",
     * keyID); headers.add("X-NCP-APIGW-API-KEY", secretKey); headers.add("charset",
     * "UTF-8"); RestTemplate restTemplate = new RestTemplate();
     * 
     * return restTemplate.postForEntity(clovaUrl, new
     * HttpEntity<byte[]>(data.getBytes(), headers), byte[].class); }
     */

}