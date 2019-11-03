package com.rain.tpl.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tpl_team_tbl")
public class Team {
	
	@Column(name="tpl_owner_id")
	private String ownerId;
	
	@Column(name="tpl_team_name")
	private String teamName;
	
	@Id
	@GenericGenerator(name = "team_id", strategy = "com.rain.tpl.idgenerator.TeamIdGenerator")
	@GeneratedValue(generator="team_id")
	@Column(name="tpl_team_id")
	private String teamId;
	
	@Column(name="tpl_sys_host")
	private String host;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Team(String ownerId, String teamName, String host) {
		super();
		this.ownerId = ownerId;
		this.teamName = teamName;
		this.host = host;
	}
	
	
}
