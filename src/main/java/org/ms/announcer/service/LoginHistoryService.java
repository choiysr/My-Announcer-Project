package org.ms.announcer.service;

import java.time.LocalDate;

/**
 * LoginHistoryService
 */
public interface LoginHistoryService {
    public void userLogin(LocalDate date , String userName);
    
}