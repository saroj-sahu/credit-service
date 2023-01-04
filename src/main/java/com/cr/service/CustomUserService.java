package com.cr.service;

import com.cr.entity.User;
import com.cr.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user=userDetailsRepository.findByUserName(username);
		
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("User Not Found with userName "+username);
		}
		return user.get();
	}

}
