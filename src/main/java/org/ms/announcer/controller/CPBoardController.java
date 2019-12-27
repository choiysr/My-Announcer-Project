package org.ms.announcer.controller;

import static org.ms.announcer.utils.FileUtil.audioSave;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.List;

import org.ms.announcer.domain.CPBoard;
import org.ms.announcer.service.CPBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Setter;

@RestController
@RequestMapping("/rcpboard/*")
public class CPBoardController {

    @Setter(onMethod_ = { @Autowired })
    private CPBoardService service;

    @PostMapping(value = "/register")
    public void register(@RequestBody CPBoard[] boards) {
        
        for(CPBoard board : boards) {
            System.out.println("실행확인");
            service.register(board);
        }
    }

    @PostMapping(value = "/registerFiles")
    public ResponseEntity<List<String>> registerFiles(MultipartFile[] files) {
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
        return new ResponseEntity<>(list, OK);
    }

}