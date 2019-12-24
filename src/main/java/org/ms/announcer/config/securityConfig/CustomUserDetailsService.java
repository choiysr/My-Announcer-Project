package org.ms.announcer.config.securityConfig;

import org.ms.announcer.domain.CustomUser;
import org.ms.announcer.domain.MemberVO;
import org.ms.announcer.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * CustomUserDetailsService
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired 
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO vo = memberRepository.findByMemberid(username).get();
		System.out.println(username);
		return vo == null ? null : new CustomUser(vo);
	}
}