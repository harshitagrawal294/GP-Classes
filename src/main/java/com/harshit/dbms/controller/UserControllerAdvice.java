package com.harshit.dbms.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {
    @ModelAttribute("curUsername")
    public String username(Principal principal) {
        return principal == null ? null : principal.getName();
    }
    @ModelAttribute("curRole")
    public String role(Authentication authentication) {
    	if(authentication==null) return null;
    	try {
        	String role=(new ArrayList<GrantedAuthority>(((UserDetails) authentication.getPrincipal()).getAuthorities())).get(0).getAuthority();
        	return role;
    	}catch(Exception e){
    		return null;
    	}
    	
    }
}
