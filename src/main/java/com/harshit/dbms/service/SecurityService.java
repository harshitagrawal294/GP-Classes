package com.harshit.dbms.service;

public interface SecurityService {
	
	String findLoggedInUsername();
    void autoLogin(String username, String password);

}
