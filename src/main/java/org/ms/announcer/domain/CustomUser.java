package org.ms.announcer.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * CustomUser
 */
public class CustomUser extends User {
    @Autowired
    private MemberVO member;
    private static final long serialVersionUID = 1L;
  
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(MemberVO vo){
        super(vo.getId(), vo.getMemberpassword(), vo.getRoles().stream().map(auth -> new SimpleGrantedAuthority(auth.getRoleName())).collect(Collectors.toList()));
        this.member = vo;
    }

   

    
}