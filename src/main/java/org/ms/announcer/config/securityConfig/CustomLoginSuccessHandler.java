package org.ms.announcer.config.securityConfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * CustomLoginSuccessHandler
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse repo, Authentication auth)
            throws IOException, ServletException {
                
                // System.out.println(auth.getPrincipal().toString());
                // System.out.println(auth.getCredentials().toString());
                Cookie userName = new Cookie("userName", auth.getName());
                System.out.println("=========================================================================");


                //24시간 세션 유지
                HttpSession session = req.getSession(false);

                if(session != null){
                    session.setMaxInactiveInterval(60*60*24);
                }

                repo.addCookie(userName);
                repo.sendRedirect("/bcboard/todayList");
    }

    
}