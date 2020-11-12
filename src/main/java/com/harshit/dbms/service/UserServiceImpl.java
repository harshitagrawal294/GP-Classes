//package com.harshit.dbms.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.harshit.dbms.dao.UserDAO;
//import com.harshit.dbms.model.User;
//
//
//@Service
//public class UserServiceImpl implements UserService {
//	
//	@Autowired
//	private UserDAO userDAO;
//	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	@Override
//	public void save(User user) {
//		
//		
//		userDAO.save(user);
//	}
//	
//	@Override
//	public User findByUsername(String username) {
//		return userDAO.findByUsername(username);
//	}
//	
//	
//
//}
