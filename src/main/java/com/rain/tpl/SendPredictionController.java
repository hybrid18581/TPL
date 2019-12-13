package com.rain.tpl;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rain.tpl.dao.LeagueDAO;
import com.rain.tpl.dao.PredictionDAO;
import com.rain.tpl.dao.UtilityDOA;
import com.rain.tpl.model.League;
import com.rain.tpl.model.Prediction;
import com.rain.validator.SendPredictionValidator;








@Controller
public class SendPredictionController {
	
	String acknowledgment="";
	UtilityDOA obj= new UtilityDOA();
	 @Autowired
	SendPredictionValidator validator;
	
	@RequestMapping(value="/SendPrediction",method=RequestMethod.GET)
	public String getPrediction(Model model,HttpSession session)
	{
		
		model.addAttribute( "predictObj",new Prediction());
		
		model.addAttribute(  "acknowldgement", acknowledgment);
		model.addAttribute("predicionLock",session.getAttribute("predicionLock"));
		model.addAttribute("gameweek",session.getAttribute("gameweek"));
		return "SendPrediction";
		
		
	}
	@RequestMapping(value="/SendPrediction",method=RequestMethod.POST)
	public String postPrediction(Model model,@ModelAttribute("predictObj")Prediction predictObj,Errors errors,HttpSession session)  
	{
		validator.validate(predictObj, errors);
		Map<String, String> clubScorer= new LinkedHashMap<String, String>();
		clubScorer.put("Select","");
		 clubScorer=getclubScorer(predictObj.getPredictionTeam());
		 model.addAttribute(  "clubScorer", clubScorer);
		 Map<String, String> opponentList= new LinkedHashMap<String, String>();
		 opponentList.put("Select","");
		 opponentList=getclubOpponent(predictObj.getPredictionTeam());
		 model.addAttribute(  "opponentList", opponentList);
		if(errors.hasErrors())
		{
			model.addAttribute("hasErrors", "Y");
			//model.addAttribute("predictObj", new Prediction());
			return "SendPrediction";
		}
		else
		{
			PredictionDAO prObj = new PredictionDAO();
			String host = "";
			try {
				InetAddress ip = InetAddress.getLocalHost();
				host = ip.getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String gameweek = session.getAttribute("gameweek").toString();
			predictObj.setGameWeek(gameweek);
			predictObj.setHost(host);
			predictObj.setPredictionSent("Y");
			boolean result=prObj.savePredcitions(predictObj);
			if(result)
			{
				acknowledgment="predictionSuccess";
			}
			else
			{
				acknowledgment="predictionFailure";
			}
			return "redirect:SendPrediction";
		}
	
		
			
		
		
	}

	

	
	@RequestMapping("/getScorerList")     
	@ResponseBody
	public String check(@RequestParam String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		String json="";
	   
	    	Map<String, String> clubScorer=getclubScorer(id);
	    	ObjectMapper mapper = new ObjectMapper();
	    	//mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);        
	    	
			try {
				 json = new ObjectMapper().writeValueAsString(clubScorer);
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	        return json;
	    
	    
		
	}
	@RequestMapping("/getOpponentList")     
	@ResponseBody
	public String opponentList(@RequestParam String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		String json="";
	   
	    	Map<String, String> clubScorer=getclubOpponent(id);
	    	ObjectMapper mapper = new ObjectMapper();
	    	//mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);        
	    	
			try {
				 json = new ObjectMapper().writeValueAsString(clubScorer);
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	        return json;
	    
	    
		
	}
	@ModelAttribute("leaguePredictList")
	public List<Prediction> getleaguePredictList(HttpSession session)
	{
		String ownerId = session.getAttribute("ownerId").toString();
		String gameWeek = session.getAttribute("gameweek").toString();
		List<Prediction> result = new ArrayList<Prediction>();
		PredictionDAO obj = new PredictionDAO();
		
		
			result = obj.getPredictionLeagueList(ownerId,gameWeek);
			
			if(result.size()!=0) {
				acknowledgment="";
			}
			else
			{
				acknowledgment="noLeagues";
			}
			
			
		
		return result;
		
	}
	
	@ModelAttribute("clubList")
	public Map<String, String> getclub()
	{
		Map<String, String> clubList = new HashMap<String, String>();
	

		clubList=obj.getDropdowns("clublist","");
		
		
		return clubList;
		
	}
	
	public Map<String, String> getclubScorer(String clubKey)
	{
		
		
			Map<String, String> scorrer= new LinkedHashMap<String, String>();
			
			scorrer.putAll(obj.getDropdowns("clubplayers",clubKey));
			scorrer.put("","Select");
			return scorrer;
		
	}
	
	public Map<String, String> getclubOpponent(String clubKey)
	{
		
		
			Map<String, String> opponent= new LinkedHashMap<String, String>();
			
			opponent.putAll(obj.getDropdowns("clubopponent",clubKey));
			if(opponent.size()==1)
			{}
			else
				opponent.put("","Select");
				
			
			return opponent;
		
	}
}
