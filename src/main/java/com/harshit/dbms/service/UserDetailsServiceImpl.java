package com.harshit.dbms.service;

import com.harshit.dbms.dao.UserDAO;
import com.harshit.dbms.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Lazy
	@Autowired
	UserDAO userDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = userDAO.findByUsername(username);
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(user.isActive()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
			grantList.add(authority);
			
		}
		
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantList);
		
		
		
		return userDetails;
		
	}

}


