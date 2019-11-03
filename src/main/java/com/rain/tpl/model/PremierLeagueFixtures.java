package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tpl_premier_fixtures_tbl")
public class PremierLeagueFixtures implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="tpl_hometeam_id")
	private String homeTeamId;
	@Column(name="tpl_awayteam_id")
	 @Id
	private String awayTeamId; 
	 @Id
	@Column(name="tpl_prediction_gameweek")
	private String gameWeek;
	public String getHomeTeamId() {
		return homeTeamId;
	}
	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}
	public String getAwayTeamId() {
		return awayTeamId;
	}
	public void setAwayTeamId(String awayTeamId) {
		this.awayTeamId = awayTeamId;
	}
	public String getGameWeek() {
		return gameWeek;
	}
	public void setGameWeek(String gameWeek) {
		this.gameWeek = gameWeek;
	}
	public PremierLeagueFixtures(String homeTeamId, String awayTeamId, String gameWeek) {
		super();
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.gameWeek = gameWeek;
	}
	  

}
