package org.ms.announcer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaiscController
 */
@Controller
@RequestMapping("/sample/*")
public class BaiscController {

    @GetMapping("/ex" )
    public void getRegister() {
    }
    
}