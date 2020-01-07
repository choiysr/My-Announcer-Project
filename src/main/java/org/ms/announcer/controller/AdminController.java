package org.ms.announcer.controller;


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

    @PostMapping(value = "/update")
    public void registerFiles(@RequestBody String userMap) {
        System.out.println(userMap);
        
    }
}