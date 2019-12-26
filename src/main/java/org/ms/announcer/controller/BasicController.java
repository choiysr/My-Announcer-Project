package org.ms.announcer.controller;

import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;

/**
 * BaiscController
 */
@Controller
@RequestMapping("/*")
public class BasicController {

    @GetMapping("/bcboard/todayList")
    public void getTodayList() {
    }

    @GetMapping("/bcboard/totallist")
    public void getTotalList() {
    }

    @GetMapping("/bcboard/loginPage")
    public void getMemberRegister() {
    }

    @GetMapping("/cpboard/myPage")
    public void test() {
    }


}