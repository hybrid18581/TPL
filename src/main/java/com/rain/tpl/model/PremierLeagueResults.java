package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tpl_plresults_tbl")
public class PremierLeagueResults implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tpl_plresults__team_id")
	private String homeTeamId;
	
	@Column(name="tpl_plresults__result")
	private String result;
	
	@Id
	@Column(name="tpl_plresults__gameweek")
	private String gameweek;
	
	@Column(name="tpl_plresults_opp")
	private String opponent;
	
	@Column(name="tpl_plresults__goals")
	private String goals;
	public PremierLeagueResults(String homeTeamId, String result, String gameweek, String opponent,String goals) {
		
		this.homeTeamId = homeTeamId;
		this.result = result;
		this.gameweek = gameweek;
		this.opponent = opponent;
		this.goals=goals;
	}
	
	

}
