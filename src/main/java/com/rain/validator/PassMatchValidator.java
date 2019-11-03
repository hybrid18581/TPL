package com.rain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rain.tpl.model.Login;
import com.rain.tpl.model.Prediction;

public class PassMatchValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		Login login= new Login();
		login=(Login) target;
		if(login.getPassword().equals(login.getConfirmPassword()))
		{
			
		}
		else
		{
			login.setConfirmPassword(null);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword", "error.confirmPassword", "Passwords do not match");
			
		}
	}

}
