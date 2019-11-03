package com.rain.tpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rain.tpl.dao.LeagueDAO;
import com.rain.tpl.model.League;
import com.rain.tpl.model.Player;
import com.rain.tpl.model.PremierLeagueFixtures;
import com.rain.tpl.model.Team;
import com.rain.utility.FixtureGenerator;

@Controller
@RequestMapping(value = "/Dashboard")
public class DashController {

	String acknowledgment="";
	@RequestMapping(value = "/showForm", method = RequestMethod.GET)
	public String get(Model theModel, HttpSession session) {
		String createdLeagueYN = "";

		String ownerKey = session.getAttribute("ownerId").toString();
		List<League> leagueList = getLeagueList(ownerKey);
		if (leagueList.size() == 0) {
			createdLeagueYN = "N";
		} else {
			createdLeagueYN = "Y";

			theModel.addAttribute("leagueList", leagueList);
		}

		//Map<String, String> leagueTypeList = getleagueTypeList();
		//Map<String, String> playerNoList = getplayerNoList();
		//String id="";
		League leagueObj = new League();
		theModel.addAttribute("createdLeagueYN", createdLeagueYN);
		//theModel.addAttribute("leagueTypeList", leagueTypeList);
		//theModel.addAttribute("playerNoList", playerNoList);
		theModel.addAttribute("leagueObj", leagueObj);
		theModel.addAttribute(  "acknowldgement", acknowledgment);
		acknowledgment="";
		//theModel.addAttribute("id", id);
		
		//premier league fixtures for the current gameweek
		List<PremierLeagueFixtures>plFixtureList= getPlFixturesList(session.getAttribute("gameweek").toString()) ;
		
		theModel.addAttribute("plFixtureList", plFixtureList);
		theModel.addAttribute("gameweek", session.getAttribute("gameweek").toString());
		
		return "Dashboard";
	}

	@RequestMapping(value = "/processForm", method = RequestMethod.POST)
	public String post(@ModelAttribute("leagueObj")@Validated (League.validateCreateLig.class )League leagueObj , BindingResult error, Model theModel,HttpSession session,Model model) {
		if (error.hasErrors()) {

			String createdLeagueYN = "E";
			theModel.addAttribute("createdLeagueYN", createdLeagueYN);
			return "Dashboard";
		} else {

			// save created league
			// put an entry in the team table for newly created team by the league owner
			// put an entry in the player table for newly created player as the league owner

			LeagueDAO createLeagueDAO = new LeagueDAO();
			// ownerID is fetched from the session
			String ownerId = session.getAttribute("ownerId").toString();
			// fetch Host Address
			String host = "";
			try {
				InetAddress ip = InetAddress.getLocalHost();
				host = ip.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Team tempTeam = new Team(ownerId, leagueObj.getTeamName(), host);
			// leagueId for tempPlayer is set as "" ,as its assigned later after
			// leagueCreation in createLeague();
			Player tempPlayer = new Player("", "0", host);
			// set ownerkey to tempLeague and host address
			leagueObj.setOwnerId(ownerId);
			leagueObj.setSystemHost(host);
			leagueObj.setActiveYn("Y");
			boolean result = createLeagueDAO.createLeague(leagueObj, tempTeam, tempPlayer);
			if (result) {
				acknowledgment="leagueCreated";
				return "redirect:showForm";
			}
			else
			{
				acknowledgment="leagueCreationFailed";
				return "redirect:showForm";
			}

			

		}

	}

	// Use model attributes to keep the value of dropdown list even after validation
	// fail
	@ModelAttribute("leagueTypeList")
	Map<String, String> getleagueTypeList() {
		Map<String, String> leagueTypeList = new HashMap<String, String>();
		leagueTypeList.put("Private", "Private");
		leagueTypeList.put("Public", "Public");
		return leagueTypeList;

	}

	@ModelAttribute("playerNoList")
	Map<String, String> getplayerNoList() {
		Map<String, String> playerNoList = new HashMap<String, String>();
		//playerNoList.put("10", "10");
		playerNoList.put("20", "20");
		return playerNoList;

	}

	@ModelAttribute("leagueList")
	List<League> getLeagueList(String ownerKey) {
		LeagueDAO obj = new LeagueDAO();
		List<League> leagueList = obj.fetchLeagueList(ownerKey);
		return leagueList;

	}
	
	@ModelAttribute("plFixtureList")
	List<PremierLeagueFixtures> getPlFixturesList(String gameWeek) {
		LeagueDAO obj = new LeagueDAO();
		List<PremierLeagueFixtures> plFixtureList = obj.getPlFixturesList(gameWeek);
		return plFixtureList;

	}
	
	
	@RequestMapping(value = "/generateFixtures", method = RequestMethod.POST,params = "generateFixtures")
	public String generateFixtures(Model theModel, HttpSession session,@ModelAttribute("leagueObj") League leagueObj) {
		
		LeagueDAO obj = new LeagueDAO();
		//check if the league is full or not 
		// result of the following function must be zero to generate fixtures
		// league can't have less or more  than selected number of players
		
		String value=obj.canGenerateFixtures(leagueObj.getLeagueId());
		if(value.equalsIgnoreCase("0")||value.equalsIgnoreCase("0"))
		{
			//static function called using classname to generate fixtures for a league
			String[][] fixtures=FixtureGenerator.generatorFixture(leagueObj.getPlayerNo());
	
			
			boolean result=obj.createFixtures(fixtures, leagueObj.getLeagueId());
			acknowledgment="fixturesGeneratedS";
			
		}
		else
		{
			// include fixtures cant be generated message
			acknowledgment="fixturesGeneratedF";
		}
		return "redirect:showForm";
		
	}
	@RequestMapping(value = "/generateFixtures", method = RequestMethod.POST,params = "deleteLeague")
	public String deleteLeague(Model theModel, HttpSession session) {
		
		
				

		
		return "";
	}
	
}
