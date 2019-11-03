package com.rain.tpl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import org.springframework.stereotype.Controller;


@Entity
@Table(name = "tpl_league_tbl")
public class League {
	public League() {

	}

	// interface to create a validation group to search the league to join
	public interface validateSrchLig {

	}

	// interface to create a validation group to create the league to create
	public interface validateCreateLig {

	}

	// interface to create a validation group to create the league to join
	public interface validatejoinLeague {

	}

	@Column(name = "tpl_owner_id")
	private String ownerId;

	@NotBlank(groups = { validateSrchLig.class }, message = "is required")
	@NotBlank(groups = { validateCreateLig.class }, message = "is required")
	@Column(name = "tpl_league_name")
	private String leagueName;

	@NotBlank(groups = { validateSrchLig.class }, message = "is required")
	@NotBlank(groups = { validateCreateLig.class }, message = "is required")
	@Column(name = "tpl_league_type")
	private String leagueType;

	@NotBlank(groups = { validateCreateLig.class }, message = "is required")
	@Column(name = "tpl_league_playerNo")
	private String playerNo;

	@Id
	@GenericGenerator(name = "league_id", strategy = "com.rain.tpl.idgenerator.LeagueIdGenerator")
	@GeneratedValue(generator = "league_id")
	@Column(name = "tpl_league_id")
	// in jsp it should be used as leagueId because jsp uses lowercase by default
	private String LeagueId;

	@Column(name = "tpl_league_code")
	private String leagueCode;

	@Column(name = "tpl_sys_host")
	private String systemHost;

	@Transient
	@NotBlank(groups = { validateCreateLig.class }, message = "is required")
	@NotBlank(groups = { validatejoinLeague.class }, message = "is required")
	private String teamName;

	@Column(name = "tpl_league_activeyn")
	private String activeYn;

	@Column(name = "tpl_league_fixtures_generated")
	private String fixturesGeneratedYN;

	public String getFixturesGeneratedYN() {
		return fixturesGeneratedYN;
	}

	public void setFixturesGeneratedYN(String fixturesGeneratedYN) {
		this.fixturesGeneratedYN = fixturesGeneratedYN;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueType() {
		return leagueType;
	}

	public void setLeagueType(String leagueType) {
		this.leagueType = leagueType;
	}

	public String getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(String playerNo) {
		this.playerNo = playerNo;
	}

	public String getLeagueId() {
		return LeagueId;
	}

	public void setLeagueId(String LeagueId) {
		this.LeagueId = LeagueId;
	}

	public String getLeagueCode() {
		return leagueCode;
	}

	public void setLeagueCode(String leagueCode) {
		this.leagueCode = leagueCode;
	}

	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
