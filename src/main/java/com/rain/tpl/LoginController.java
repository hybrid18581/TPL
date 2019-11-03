package com.rain.tpl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/showLoginPage")
	public String loginForm()
	{
		
		return "LoginPage";
		
	}

}
