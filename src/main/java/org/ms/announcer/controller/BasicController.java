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
@RequestMapping("/bcboard/*")
public class BasicController {

    @Setter(onMethod_ = { @Autowired })
    BCBoardService bcboardService;

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