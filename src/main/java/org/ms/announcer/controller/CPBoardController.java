package org.ms.announcer.controller;

import static org.ms.announcer.utils.FileUtil.*;
import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.service.CPBoardService;
import org.ms.announcer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Setter;

@RestController
@RequestMapping("/rcpboard/*")
public class CPBoardController {

    @Setter(onMethod_ = { @Autowired })
    private CPBoardService cpbService;

    @Setter(onMethod_ = { @Autowired })
    private MemberService memService;

    // =======================================================CREATE 
    @PostMapping(value = "/register")
    public void register(@RequestBody CPBoard[] boards) {
        for (CPBoard board : boards) {
            cpbService.register(board);
        }
    }

    @PostMapping(value = "/registerFiles")
    public ResponseEntity<List<String>> registerFiles(MultipartFile[] files) {
        System.out.println(files[0]);
        List<String> list = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String fileNameWithoutType = file.getOriginalFilename().substring(0,
                        file.getOriginalFilename().lastIndexOf("."));
                list.add(audioSave(fileNameWithoutType, file.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("아아아아아");
        return new ResponseEntity<>(list, OK);
    }


    // =======================================================READ
    @GetMapping(value = "/read/{bno}")
    public ResponseEntity<CPBoard> read(@PathVariable("bno") Integer bno) {
        CPBoard board = cpbService.getOneCPBoard(bno);
        return new ResponseEntity<>(board, OK);
    }


    // =======================================================modify
    @PutMapping(value = "/modify")
    public void modify(@RequestBody CPBoard board) {
        System.out.println("멤버정보 잘 들어왔을까요");
        System.out.println(board.getMember());
        CPBoard prevBoard = cpbService.getOneCPBoard(board.getBno());
        // 기존 파일을 유지시키는 경우 
        if(board.getFile_name().equals("KEEP")&&board.getFile_path().equals("KEEP")) {
            board.setFile_path(prevBoard.getFile_path());
            board.setFile_name(prevBoard.getFile_name());
        }
        cpbService.update(board);
    }


    
    @GetMapping(value = "/getUserInfo")
    public ResponseEntity<MemberVO> getUserInfo(String userName) {
        MemberVO vo = cpbService.getCP(userName);

        return new ResponseEntity<>(vo, OK);
    }

    @GetMapping(value = "/getImg")
    public ResponseEntity<byte[]> getImg(String fileName) {
        makeThumnailPath();
        File file = new File(fileName);
        HttpHeaders header = new HttpHeaders();
        ResponseEntity<byte[]> result=null;
        try {
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }

   
 

    // =======================================================APPEND LIST
    @GetMapping("/getCPBoardList/{mid}")
    public ResponseEntity<List<CPBoard>> getList(@PathVariable("mid") String mid) {
        MemberVO member = new MemberVO();
        member.setId(mid);
        List<CPBoard> result = new ArrayList<>();
        result = cpbService.getCPBoardList(member);
        return new ResponseEntity<>(result, OK);
    }


    




}