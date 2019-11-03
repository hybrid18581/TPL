package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="tpl_player_tbl")
public class Player implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tpl_player_srno")
	private int srNo;
	
	@Column(name="tpl_league_id")
	private String leagueId;
	
	@Column(name="tpl_team_id")
	private String teamId;
	
	@Column(name="tpl_player_ponints")
	private String playerPoints;
	
	@Column(name="tpl_sys_host")
	private String host;

	
	public Player()
	{
		
	}


	public Player(String leagueId, String playerPoints, String host) {
		
		this.leagueId = leagueId;
		
		this.playerPoints = playerPoints;
		this.host = host;
	}


	public String getLeagueId() {
		return leagueId;
	}


	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}


	public String getTeamId() {
		return teamId;
	}


	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}


	public String getPlayerPoints() {
		return playerPoints;
	}


	public void setPlayerPoints(String playerPoints) {
		this.playerPoints = playerPoints;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}
	

}
