package org.ms.announcer.controller;

import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.service.AdminService;
import org.ms.announcer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 */
@RestController
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    private AdminService aservice;

    @Autowired
    private MemberService mservice;


    @PostMapping(value = "/update")
    public void registerFiles(@RequestBody String userMap) {
        System.out.println(userMap);
    }

    @GetMapping("/getCountsByDay/{today}")
    public ResponseEntity<List<Map<String,Integer>>> getCountsByDay(@PathVariable("today") String today) {
        LocalDate date = LocalDate.parse(today);
        List<Map<String,Integer>> result =  aservice.getCountsByDay(date);
        return new ResponseEntity<>(result,OK);
    }

    @GetMapping("/getAllMember")
    public ResponseEntity<List<MemberVO>> getAllMembers() {
        List<MemberVO> result = new ArrayList<>();
        result = mservice.getAllMembers();
        return new ResponseEntity<>(result,OK);
    }


}