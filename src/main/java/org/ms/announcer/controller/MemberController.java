package org.ms.announcer.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * MemberController
 */

@RestController
@RequestMapping("/member/*")
public class MemberController {

    @Autowired
    private MemberService ms;

    @PostMapping("register")
    public void register(@RequestBody MemberVO vo) {
        System.out.println(vo.getType());
        ms.RegistMemeber(vo);
    }

    @GetMapping("checkOverlap/{memberid}")
    public boolean checkOverlap(@PathVariable("memberid") String memberid) {
        return ms.checkOvaelap(memberid);
    }

    @PostMapping(value = "/modifyCPInfo")
    public void modifyCPInfo(@RequestBody MemberVO vo) {
        System.out.println(vo.getCpInfo().getImgFile());
        ms.updateCPinfo(vo);

    }

    @PostMapping(value = "/uploadImg")
    public String uploadImg(MultipartFile uploadFile, String userName) {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + uploadFile.getOriginalFilename();

        File saveFile = new File("C:\\CpImg", fileName);
        try {
            uploadFile.transferTo(saveFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return fileName;
    }

    @GetMapping("getCPInfo")
    public ResponseEntity<MemberVO> getCPInfo(String title, int page) {
        Page<MemberVO> list = ms.getAllCPByTilte(title, page);

       return new ResponseEntity<>(new MemberVO(), HttpStatus.OK);
   }

}