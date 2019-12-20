package org.ms.announcer.controller;

import static org.ms.announcer.utils.FileUtil.audioSave;
import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.util.FileUtil;
import org.ms.announcer.domain.BCBoardDTO;
import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PostMapping(value = "/fileUpload") // 얘는 왜 get으로 안되는가? > byte너무길어서 url로 보낼수 없음
    public ResponseEntity<List<String>> fileUpload(MultipartFile additionalAudio) {
        // 경로만 hidden으로 리턴해줘서 나중에 prelisten누르면 같이 보내줄것임. (prelisten수정 view and
        // controller)
        List<String> list = new ArrayList<>();
        String fileNameWithoutType = additionalAudio.getOriginalFilename().substring(0,
                additionalAudio.getOriginalFilename().lastIndexOf("."));
        try {
            list.add(audioSave("tmp" + fileNameWithoutType, additionalAudio.getBytes()).replace("\\", "-"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(list, OK);
    }

    @PostMapping(value = "/registerFiles")
    public ResponseEntity<List<String>> registerFiles(MultipartFile additionalAudio) {
        List<String> list = new ArrayList<>();
        String fileNameWithoutType = additionalAudio.getOriginalFilename().substring(0,
                additionalAudio.getOriginalFilename().lastIndexOf("."));
        try {
            list.add(audioSave(fileNameWithoutType, additionalAudio.getBytes()).replace("\\", "*"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(list, OK);
    }

    @GetMapping(value = "/getPrevFile/{prevPath}")
    public ResponseEntity<List<String>> getPrevFiles(@PathVariable("prevPath") String prevPath) {
        System.out.println("파라미터확인");
        System.out.println(prevPath);
        File file = new File(prevPath.replace("-", "\\"));
        System.out.println("에러체크1");
        List<String> list = new ArrayList<>();
        String fileNameWithoutType = prevPath.substring(prevPath.lastIndexOf("_")+1, prevPath.lastIndexOf("."));
        try {
            byte[] fileBytes = FileUtil.readAsByteArray(file);
            list.add(audioSave(fileNameWithoutType, fileBytes).replace("\\", "*"));
        } catch (Exception e) {
            System.out.println("getprevfile method에서 에러");
            e.printStackTrace();
        }
        System.out.println("리턴할 string"+list.get(0));
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
        ResponseEntity<byte[]> response = makeAudio(dto);
        String wholePath = audioSave(dto.getTitle(), response.getBody());
        String pathWithoutFname = wholePath.substring(0, wholePath.lastIndexOf("\\") + 1); // 파일명을 제외한 경로.뒤에 슬래시 포함
        String fileName = wholePath.substring((pathWithoutFname.length()), wholePath.length()); // uuid+파일명
        dto.getAudioVO().setAudioPath(pathWithoutFname);
        dto.getAudioVO().setAudioName(fileName);
        service.register(dto);
    }

    // get 방식으로 url dptj 원하는 페이지, 오늘 날짜를 받는다.
    @GetMapping("/todayList/{startdate}/{week}")
    public ResponseEntity<Page<BCBoardDTO>> list(@PathVariable("startdate") String startdate,
            @PathVariable("week") String week) {

        // 정렬하여 값 가져올 기준
        Pageable page = PageRequest.of(0, 300, Direction.ASC, "starttime");
        Page<BCBoardDTO> result = service.getTodayList(startdate, week, page);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/totalList")
    public ResponseEntity<Map<String, Object>> getTotalList(
            @PageableDefault(page = 0, direction = Direction.ASC, sort = { "startdate", "starttime" }) Pageable page,
            String category, String search) {
        Map<String, Object> result = service.getAllList(page, category, search);
        return new ResponseEntity<>(result, OK);
    }

    // 조회
    @GetMapping("/read/{bno}")
    public ResponseEntity<BCBoardDTO> getOneBCBoard(@PathVariable("bno") Integer bno) {
        return new ResponseEntity<>(service.read(bno), OK);
    }

    // 삭제
    @DeleteMapping("/{bno}")
    public void deleteBCBoard(@PathVariable("bno") Integer bno) {
        service.delete(bno);
    }

    // 수정
    @PutMapping("/modify")
    public void updateBCBoard(@RequestBody BCBoardDTO dto) {
        System.out.println("put 진입확인");
        System.out.println("dto변경내역이 들어왔나 확인");
        System.out.println(dto);
        ResponseEntity<byte[]> response = makeAudio(dto);
        String wholePath = audioSave(dto.getTitle(), response.getBody());
        String pathWithoutFname = wholePath.substring(0, wholePath.lastIndexOf("\\") + 1); // 파일명을 제외한 경로.뒤에 슬래시 포함
        String fileName = wholePath.substring((pathWithoutFname.length()), wholePath.length()); // uuid+파일명
        dto.getAudioVO().setAudioPath(pathWithoutFname);
        dto.getAudioVO().setAudioName(fileName);
        service.update(dto);
    }

    ////////// API METHOD ////////////

    public ResponseEntity<byte[]> makeAudio(BCBoardDTO dto) {

        String data = "";
        String url = "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String clovaUrl = "https://naveropenapi.apigw.ntruss.com/voice/v1/tts";
        String clovaUrlPremium = "https://naveropenapi.apigw.ntruss.com/voice-premium/v1/tts";
        //보안상의 이유로 삭제
        String keyID = "";
        String secretKey = "";

        headers.add("charset", "UTF-8");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("X-NCP-APIGW-API-KEY-ID", keyID);
        headers.add("X-NCP-APIGW-API-KEY", secretKey);

        if (dto.getGender().equals("man")) {
            data = "speaker=jinho&speed=0&text=" + dto.getContent();
            url = clovaUrl;
        } else {
            data = "speaker=nara&speed=0&format=wav&text=" + dto.getContent();
            url = clovaUrlPremium;
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
