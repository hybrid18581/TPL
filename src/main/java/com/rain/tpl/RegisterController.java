package com.rain.tpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rain.tpl.dao.LoginDAO;
import com.rain.tpl.dao.UtilityDOA;
import com.rain.tpl.model.Login;
import com.rain.validator.PassMatchValidator;

@Controller
@RequestMapping(value = "/Register")
public class RegisterController {

	String acknowledgment="";
	@Autowired
	PassMatchValidator validator;
	
	@RequestMapping(value = "/processregisterForm",method = RequestMethod.POST)
	public String registersh(@Valid @ModelAttribute("login")Login login, BindingResult errors, ModelMap model) {
		
		if(errors.hasErrors())
		{
			return "Register";
		}
		else
		{
			validator.validate(login, errors);
			if(errors.hasErrors())
			{
				return "Register";
			}
			else
			{
			LoginDAO loginDao= new LoginDAO();
		boolean result=	loginDao.saveLogin(login);
		if(result)
		{
			acknowledgment="registeredSuccessfully";
			
			return "redirect:getRegisterForm";
		}
		else
		{
			acknowledgment="registrationFailed";
			
			return "redirect:getRegisterForm";
		}
			
		}

		}

	}
	
	@RequestMapping(value = "/getRegisterForm",method = RequestMethod.GET)
	public String get(ModelMap model) {

		
		Map<String, String> countryList = getCountry();
		Map<String, String> clubList =getclub();
		
		

		Login login = new Login();
		model.addAttribute("clubList", clubList);
		model.addAttribute("countryList", countryList);
		model.addAttribute("login", login);
		model.addAttribute(  "acknowldgement", acknowledgment);
		acknowledgment="";
		return "Register";
	}
	
	//Use model attributes to keep the value of dropdown list even after validation fail
	@ModelAttribute("countryList")
	public Map<String, String> getCountry()
	{
		Map<String, String> countryList = new HashMap<String, String>();
		countryList.put("ind", "India");
		
		return countryList;
		
	}
	@ModelAttribute("clubList")
	public Map<String, String> getclub()
	{
		Map<String, String> clubList = new HashMap<String, String>();
		
		UtilityDOA obj= new UtilityDOA();
		clubList=obj.getDropdowns("eplclubs","");
		
		return clubList;
		
	}
}
