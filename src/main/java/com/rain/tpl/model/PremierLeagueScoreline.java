package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tpl_plscoreline_tbl")
public class PremierLeagueScoreline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tpl_plresults__team_id")
	private String homeTeamId;
	
	@Column(name = "tpl_plresults__goals")
	private String goals;
	
	@Id
	@Column(name = "tpl_plresults__gameweek")
	private String gameweek;
	
	@Column(name = "tpl_plscoreline_opp")
	private String opponent;

	public PremierLeagueScoreline(String homeTeamId, String goals, String gameweek, String opponent) {
		
		this.homeTeamId = homeTeamId;
		this.goals = goals;
		this.gameweek = gameweek;
		this.opponent = opponent;
	}

}
