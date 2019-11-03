package com.rain.tpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

@Controller
// @RequestMapping(name="/joinLeagues")
public class JoinLeagueController {

	String acknowledgment = "";

	@RequestMapping(value = "/joinLeagues", method = RequestMethod.GET)
	public String getJoinLeagues(Model model) {
		model.addAttribute("leagueObj", new League());
		model.addAttribute("acknowldgement", acknowledgment);
		acknowledgment = "";
		return "joinLeagues";

	}

	@RequestMapping(value = "/searchLeagues", method = RequestMethod.POST, params = "searchLeague")
	public String searchLeagues(Model model,
			@ModelAttribute("leagueObj") @Validated(League.validateSrchLig.class) League leagueObj, BindingResult error,
			HttpSession session) {
		String ownerId = session.getAttribute("ownerId").toString();
		if (error.hasErrors()) {
			return "joinLeagues";
		} else {
			List<League> result = new ArrayList<League>();
			LeagueDAO obj = new LeagueDAO();

			result = obj.searchLeague(leagueObj.getLeagueName(), leagueObj.getLeagueType());
			if(result.size()!=0)
			{
			model.addAttribute("leagueJoinList", result);
			model.addAttribute("leagueObj", new League());
			return "joinLeagues";
			}
			else
			{
				model.addAttribute("acknowledgment", "noLeagueFound");
				return "joinLeagues";
			}

		}
	}

	@RequestMapping(name = "/searchLeagues", method = RequestMethod.POST, params = "joinLeagues")
	public String joinLeagues(Model model,
			@ModelAttribute("leagueObj") @Validated(League.validatejoinLeague.class) League leagueObj,
			BindingResult error, HttpSession session) {
		if (error.hasErrors()) {
			return "joinLeagues";
		} else {
			LeagueDAO obj = new LeagueDAO();
			String ownerId = session.getAttribute("ownerId").toString();
			String host = "";
			try {
				InetAddress ip = InetAddress.getLocalHost();
				host = ip.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// check if max players have joined the league or no
			String leagueFull = obj.canGenerateFixtures(leagueObj.getLeagueId());
			if (leagueFull.equalsIgnoreCase("0") || leagueFull.equalsIgnoreCase("0")) {

				// league full

				acknowledgment = "leaguefull";

				return "redirect:/joinLeagues";
			} else {
				String chcker = obj.checkIfLeagueJoined(leagueObj.getLeagueId(), ownerId);
				if (chcker == null || chcker.equalsIgnoreCase("")) {
					boolean returns = obj.joinLeague(leagueObj.getLeagueId(), leagueObj.getTeamName(), ownerId, host);
					if (returns) {
						acknowledgment = "joinedsuccess";
						return "redirect:/joinLeagues";
					}

					else {

						acknowledgment = "joinedfailed";

						return "redirect:/joinLeagues";
					}
				} else {
					acknowledgment = "alreadyjoined";

					return "redirect:/joinLeagues";
				}

			}

		}
	}

}
