package org.ms.announcer.controller;

import org.ms.announcer.service.BCBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Setter;

/**
 * BaiscController
 */
@Controller
@RequestMapping("/bcboard/*")
public class BasicController {

    @Setter(onMethod_ = {@Autowired} )
    BCBoardService bcboardService;

    @GetMapping("/register" )
    public void getRegister() {
        System.out.println("들어왔나요??");
    }

    @GetMapping("/bclist" )
    public void getbclist(Model model) {
        model.addAttribute("list", bcboardService.getlist());
    }
    
}