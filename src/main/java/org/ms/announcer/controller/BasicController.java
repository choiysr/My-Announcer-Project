package org.ms.announcer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaiscController
 */
@Controller
@RequestMapping("/bcboard/*")
public class BasicController {


    @GetMapping("/todayList")
    public void getTodayList() {
    }

    @GetMapping("/totallist")
    public void getTotalList() {
    }

    @GetMapping("/loginPage")
    public void getMemberRegister() {
    }

}