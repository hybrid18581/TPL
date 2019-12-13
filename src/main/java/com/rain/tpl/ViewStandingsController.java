package com.rain.tpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rain.tpl.dao.LeagueDAO;
import com.rain.tpl.model.League;

@Controller
@RequestMapping(value = "/ViewStanding")
public class ViewStandingsController {
	@RequestMapping(value = "/getStanding", method = RequestMethod.GET)
	public String getStanding(Model model, HttpSession session) {

		String ownerId = session.getAttribute("ownerId").toString();
		LeagueDAO obj = new LeagueDAO();

		List<League> leagueObjList = obj.fetchViewStandings(ownerId);
 //commit 
		model.addAttribute("leagueStanding", leagueObjList);
		model.addAttribute("gameweek", session.getAttribute("gameweek").toString());
		return "ViewStanding";
	}

	@RequestMapping("/getStandingDetails")
	@ResponseBody
	public String getStandingDetails(@RequestParam String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode arrayNode = mapper.createArrayNode();

		LeagueDAO obj = new LeagueDAO();
		List<Object[]> standings = obj.fetchStandingsDetails(id);
		for (int i = 0; i < standings.size(); i++) {
			ObjectNode jo = mapper.createObjectNode();
			jo.put("rank", standings.get(i)[0].toString());
			jo.put("teamname", standings.get(i)[1].toString());
			jo.put("GP", standings.get(i)[2].toString());
			jo.put("GF", standings.get(i)[3].toString());
			jo.put("GA", standings.get(i)[4].toString());
			jo.put("points", standings.get(i)[5].toString());
			jo.put("w", standings.get(i)[6].toString());
			jo.put("d", standings.get(i)[7].toString());
			jo.put("l", standings.get(i)[8].toString());
			arrayNode.add(jo);
		}

		try {
			json = new ObjectMapper().writeValueAsString(arrayNode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping("/getResultsFixtures")
	@ResponseBody
	public String getResultsFixtures(@RequestParam String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode arrayNode = mapper.createArrayNode();

		LeagueDAO obj = new LeagueDAO();
		List<Object[]> standings = obj.fetchResultsFixtures(id);

		Map<String, String> hashMap = new HashMap<String, String>();
		ObjectNode jo;
		String data = "";
		String gw = "";
		int count = 1;
		try {
			
			for(int j =1 ;j<=38;j++)
			{
			for (int i = 0; i < standings.size(); i++) {

				if (Integer.toString(j).equalsIgnoreCase(standings.get(i)[0].toString())) {
					// gw=standings.get(count)[0].toString();
					data = data+ "#" + standings.get(i)[1].toString() + ":" + standings.get(i)[2].toString() + ":"
							+ standings.get(i)[3].toString() + ":" + standings.get(i)[4].toString() + ":"
							+ standings.get(i)[5].toString().replace("*", " ") + ":" + standings.get(i)[6].toString().replace("*", " ") ;
				
	
				
				
				
				}

			
				

			}
			jo = mapper.createObjectNode();
			jo.put("gw", j);
			jo.put("data", data);

			arrayNode.add(jo);
			
			data="";
			}
			json = new ObjectMapper().writeValueAsString(arrayNode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
