package org.ms.announcer.service;

import java.time.LocalDate;

import org.ms.announcer.domain.LoginHistory;
import org.ms.announcer.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LoginHistoryServiceImpl
 */
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService{
    
    @Autowired
    private AdminRepository adminRepository;

	@Override
	public void userLogin(LocalDate date, String userName) {
        LoginHistory log =null;
        log = adminRepository.findHistory(date, userName);
        
        if(log == null){
            log = new LoginHistory();
            log.setLoginDate(date);
            log.setMemberId(userName);
            adminRepository.save(log);
        }

	}
}