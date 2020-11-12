package com.harshit.dbms.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.harshit.dbms.dao.UserDAO;

class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
	
	@Autowired
	private UserDAO userDAO;

    
	@Override
    public void initialize(UniqueUsername constraint) {
    }
	@Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userDAO.userExists(username);
    }
 
}


