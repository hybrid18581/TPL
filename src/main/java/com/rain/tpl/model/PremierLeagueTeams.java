package com.rain.tpl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tpl_eplclub_tbl")
public class PremierLeagueTeams {

	@Column(name="tpl_eplclub_key")
	@Id
	private String id;
	
	@Column(name="tpl_eplclub_name")
	private String name;
	
	@Column(name="tpl_eplclub_player_link")
	private String clubLink;
	
	
	@Column(name="tpl_eplclub_activeyn")
	private String activeYN="Y";
}
