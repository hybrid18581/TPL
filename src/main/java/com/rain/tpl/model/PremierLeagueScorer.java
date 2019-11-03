package com.rain.tpl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tpl_plscorer_tbl")
public class PremierLeagueScorer  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="tpl_plresults__team_id")
	private String homeTeamId;
	@Id
	@Column(name="tpl_plresults__scorer_id")
	private String scorer;
	@Column(name="tpl_plresults__gameweek")
	@Id
	private String gameweek;
	@Column(name="tpl_plscorer_opp")
	private String opponent;
	public PremierLeagueScorer(String homeTeamId, String scorer, String gameweek, String opponent) {
		
		this.homeTeamId = homeTeamId;
		this.scorer = scorer;
		this.gameweek = gameweek;
		this.opponent = opponent;
	}
	 

}
