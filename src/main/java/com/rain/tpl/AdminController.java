package com.rain.tpl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rain.tpl.dao.AdminDAO;

@Controller
@RequestMapping(value = "adminpanel")
public class AdminController {

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminHome() {

		return "admin";
	}

	@RequestMapping(value = "/lockgameweek", method = RequestMethod.GET)
	public @ResponseBody boolean lockgameweek() {

	
		
		AdminDAO adm = new AdminDAO();
		
		boolean returns = adm.lockGameweek();
		return returns;
	
		
	}
	@RequestMapping(value = "/generateresults", method = RequestMethod.GET)
	public @ResponseBody boolean generateresults() {

		AdminDAO adm = new AdminDAO();
		boolean returns = adm.generateResults();

		return returns;
	}
	@RequestMapping(value = "/plteamdatasave", method = RequestMethod.GET)
	public @ResponseBody boolean booleanplteamdatasave() {

		AdminDAO adm = new AdminDAO();
		boolean returns = adm.getData();

		return returns;
	}
	
	@RequestMapping(value = "/gameWeekFixtures", method = RequestMethod.GET)
	public @ResponseBody boolean getGameWeekFixtures(HttpSession request) {
		String gw=request.getAttribute("gameweek").toString();
		AdminDAO adm = new AdminDAO();
		boolean returns =adm.savePremierLeagueFixtures(gw);
///get-fixture-by-gameweek?gameweek=3
		return returns;
	
	}
	
	@RequestMapping(value = "/getGameWeeScores", method = RequestMethod.GET)
	
	public @ResponseBody boolean getGameWeeScores(HttpSession session) {
		
		String gw=session.getAttribute("gameweek").toString();
		AdminDAO adm = new AdminDAO();
		boolean returns =adm.savePremierLeagueScores(gw);
///get-fixture-by-gameweek?gameweek=3
		return returns;
		 
		
		
	}
}
