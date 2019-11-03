package com.rain.tpl;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rain.tpl.dao.LoginDAO;
import com.rain.tpl.model.Login;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session,Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		
		
		
		
		
		@SuppressWarnings("unchecked")
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) 
				SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		LoginDAO obj=new LoginDAO();
		List<Object[]> adminData= obj.fetchGameweekdata();
		
		
		for (Object[] row : adminData) {
			session.setAttribute("gameweek", row[1].toString());
			session.setAttribute("predicionLock",  row[0].toString());
		}
		if(authorities.toString(). equalsIgnoreCase("[ROLE_admin]"))
			
		{
			return "redirect:adminpanel/admin";
		}
		//fetch user details
				
				Login userDetails =obj.fetchUserDetails(userName);
				session.setAttribute("userName", userName);
				session.setAttribute("ownerId", userDetails.getId());
				
				//call to fetch gameweekdetails
			
				
				
		
		return "redirect:Dashboard/showForm";
	}


 
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public String accessDenied() {

		return "access-denied";
	}
	
	@RequestMapping(value = "/rules", method = RequestMethod.GET)
	public String rules() {

		return "rules";
	}
}
