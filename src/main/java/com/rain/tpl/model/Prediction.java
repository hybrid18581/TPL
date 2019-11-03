package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;






@Entity
@Table(name="tpl_prediction_tbl")
public class Prediction implements Serializable{
	public Prediction()
	{
		
	}
	@NotBlank( message = "is required")
	@Column(name="tpl_prediction_type")
	private String predictionType;
	
	@NotBlank( message = "is required")
	@Column(name="tpl_predicted_team")
	private String predictionTeam;
	
	@Column(name="tpl_predicted_scorer")
	private String predictionScorer;
	
	@Column(name="tpl_predicted_scoreline")
	
	private String predictionScoreline;
	
	@Id
	@Column(name="tpl_league_id")
	private String leagueId;
	
	@Id
	@Column(name="tpl_team_id")
	private String teamId;
	
	@Transient
	private String leagueName;
	
	@Transient
	private String teamName;
	
	@Id
	@Column(name="tpl_prediction_gameweek")
	private String gameWeek;
	
	@Column(name="tpl_sys_host")
	private String host;
	
	@Column(name="tpl_prediction_sent")
	private String predictionSent;
	
	@NotBlank( message = "is required")
	@Column(name="tpl_predicted_opponent")
	private String opponent;
	
	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getPredictionSent() {
		return predictionSent;
	}

	public void setPredictionSent(String predictionSent) {
		this.predictionSent = predictionSent;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPredictionType() {
		return predictionType;
	}

	public void setPredictionType(String predictionType) {
		this.predictionType = predictionType;
	}

	public String getPredictionTeam() {
		return predictionTeam;
	}

	public void setPredictionTeam(String predictionTeam) {
		this.predictionTeam = predictionTeam;
	}

	public String getPredictionScorer() {
		return predictionScorer;
	}

	public void setPredictionScorer(String predictionScorer) {
		this.predictionScorer = predictionScorer;
	}

	public String getPredictionScoreline() {
		return predictionScoreline;
	}

	public void setPredictionScoreline(String predictionScoreline) {
		this.predictionScoreline = predictionScoreline;
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



	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getGameWeek() {
		return gameWeek;
	}

	public void setGameWeek(String gameWeek) {
		this.gameWeek = gameWeek;
	}

}
