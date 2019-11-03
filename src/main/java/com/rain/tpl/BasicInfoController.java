package com.rain.tpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping(value = "/updatebasicdetails")
public class BasicInfoController {

	@RequestMapping(value = "/showFormDetails", method = RequestMethod.GET)
	public String getBasicDetails(Model theModel, HttpSession session) {
		
		return "BasicInfo";
	}
	
	@RequestMapping(value = "/showFormForgotPassword", method = RequestMethod.GET)
	public String getForgotPassword(Model theModel, HttpSession session) {
		
		return "ForgotPassword";
	}
	
}
