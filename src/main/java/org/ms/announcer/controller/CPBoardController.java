package org.ms.announcer.controller;

import org.ms.announcer.service.CPBoardService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;

public class CPBoardController {

    @Setter(onMethod_ = {@Autowired})
    private CPBoardService cpBoardService;
    
}