package org.ms.announcer.controller;

import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaiscController
 */
@Controller
@RequestMapping("/bcboard/*")
public class BaiscController {
    @Autowired
    BCBoardService bcboardService;

    @GetMapping("/register" )
    public void getRegister() {
    }

    @GetMapping("/bclist" )
    public void getbclist(Model model) {
    }
    
}