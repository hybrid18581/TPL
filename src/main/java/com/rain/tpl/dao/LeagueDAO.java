package com.rain.tpl.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.springframework.transaction.annotation.Transactional;

import com.rain.tpl.model.League;
import com.rain.tpl.model.Login;
import com.rain.tpl.model.Player;
import com.rain.tpl.model.PremierLeagueFixtures;
import com.rain.tpl.model.Team;
import com.rain.utility.DataBaseUtility;

public class LeagueDAO {

	

	// creates a league and puts subsequent entries in the team table and player
	// table
	
	@Transactional
	public boolean createLeague(League tempLeague, Team tempTeam, Player tempPlayer) {
		
		
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
	
		Random random = new Random();
		try {

			session.beginTransaction();
			// save league
			tempLeague.setFixturesGeneratedYN("N");
			session.save(tempLeague);
			// save team
			session.save(tempTeam);
			// assign leagueId to tempPlayers's leagueId variable
			tempPlayer.setLeagueId(tempLeague.getLeagueId());
			// assign teamId to tempPlayers's teamId variable
			tempPlayer.setTeamId(tempTeam.getTeamId());
			// save player
			session.save(tempPlayer);
			if (tempLeague.getLeagueType().equalsIgnoreCase("Private")) {
				int randomNo = random.nextInt() * ((1000 - 100) + 1) + 100;
				session.createQuery("UPDATE  League L SET L.leagueCode=:leagueCode where L.LeagueId=:leagueId")
						.setParameter("leagueId", tempLeague.getLeagueId())
						.setParameter("leagueCode", tempLeague.getLeagueId() + randomNo).executeUpdate();
			}
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		}finally {
			
			factory.close();
		}

	}

	// checks if an owner has a created at least one league or not
	
	// if list exists populates that list for a owner key
	@Transactional
	public List<League> fetchLeagueList(String ownerKey) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();

		try {
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<League> leagueList = session
					.createQuery("select L from League L where L.ownerId=:ownwerKey and L.activeYn='Y'")
					.setParameter("ownwerKey", ownerKey).getResultList();
			session.getTransaction().commit();
			session.close();
			return leagueList;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}finally {
			
			factory.close();
		}
		// .createQuery("select L.leagueName,case L.leagueType when 'PR' then 'Private'
		// else 'Public' end,L.leagueCode from League L where L.ownerId=:ownwerKey and
		// L.activeYn='Y'")
	}

	// searches for leagues with either league code is league is private or league
	// name if its public
	@Transactional
	public List<League> searchLeague(String leagueidentity, String leagueType) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		try {
			session.beginTransaction();
			if (leagueType.equalsIgnoreCase("Public")) {
				@SuppressWarnings("unchecked")
				List<League> leagueList = session.createQuery(
						"select L from League L where L.leagueName=:leagueidentity and L.activeYn='Y' and L.leagueType='Public' ")
						.setParameter("leagueidentity", leagueidentity).getResultList();
				session.getTransaction().commit();
				session.close();
				return leagueList;
			} else {
				@SuppressWarnings("unchecked")
				List<League> leagueList = session.createQuery(
						"select L from League L where L.leagueCode=:leagueidentity and L.activeYn='Y' and L.leagueType='Private'  ")
						.setParameter("leagueidentity", leagueidentity).getResultList();
				session.getTransaction().commit();
				session.close();
				return leagueList;
			}

		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}
		finally {
			
			factory.close();
		}
	}

	// user can join the league
	@Transactional
	public boolean joinLeague(String leagueId, String teamName, String ownerKey, String host) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		session.beginTransaction();
		try {
			// save team
			Team tempTeam = new Team(ownerKey, teamName, host);
			session.save(tempTeam);
			// save player
			Player tempPlayer = new Player(leagueId, "0", host);
			tempPlayer.setTeamId(tempTeam.getTeamId());
			session.save(tempPlayer);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		}finally {
			
			factory.close();
		}
	}

	// check if user has already joined the league
	@Transactional
	public String checkIfLeagueJoined(String leagueId, String ownerKey) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		session.beginTransaction();
		String result = "";
		try {
			result = (String) session.createQuery(
					"SELECT distinct P.leagueId from Player P join Team T on T.teamId=P.teamId where P.leagueId=:leagueId and T.ownerId=:ownerKey")
					.setParameter("leagueId", leagueId).setParameter("ownerKey", ownerKey).uniqueResult();
			session.getTransaction().commit();
			session.close();
		}

		catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();

		}
		finally {
			
			factory.close();
		}
		return result;

	}
	@Transactional
	public boolean createFixtures(String[][] fixtures, String leagurId) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		String fixtureString = "";
		for (int i = 0; i < fixtures.length; i++) {
			//System.out.println("Round " + (i + 1));
			//System.out.println(Arrays.asList(fixtures[i]));
			if (i == 0) {
				fixtureString = Arrays.asList(fixtures[i]).toString();
			} else {
				fixtureString = fixtureString + ":" + Arrays.asList(fixtures[i]);
			}

		}

		
		try {
			session.beginTransaction();

			StoredProcedureQuery query = session.createStoredProcedureQuery("usp_leaguefixtures_save")
					.registerStoredProcedureParameter("fixtures", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("leagueid", String.class, ParameterMode.IN)
					.setParameter("fixtures", fixtureString).setParameter("leagueid", leagurId);

			query.execute();

			// Long commentCount = (Long) query.getOutputParameterValue(1);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		}
		finally {
			
			factory.close();
		}
	}

	// check if the league is full or not
	// check if max players have joined the league or no( both)
	@Transactional
	public String canGenerateFixtures(String leagueId) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		session.beginTransaction();
		String result = "";
		try {
			StoredProcedureQuery query = session.createStoredProcedureQuery("usp_cangenerate_fixtures")
					.registerStoredProcedureParameter("result", String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter("leagueid", String.class, ParameterMode.IN)

					.setParameter("leagueid", leagueId);

			query.execute();
			result = (String) query.getOutputParameterValue("result");
			session.getTransaction().commit();

			session.close();
		}

		catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();

		}
		finally {
			
			factory.close();
		}
		return result;

	}

	// fetch leagues joined by a player along with the standings
	@Transactional
	public List<League> fetchViewStandings(String ownerKey) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		List<Object[]> leagueObjList = new ArrayList<Object[]>();
		List<League> leagueList = new ArrayList<League>();
		try {
			session.beginTransaction();

			ProcedureCall procedureCall = session.createStoredProcedureCall("usp_viewstandings_populate");
			procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
			procedureCall.registerParameter(2, String.class, ParameterMode.IN);

			procedureCall.setParameter(2, ownerKey);

			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			leagueObjList = resultSetOutput.getResultList();

			session.getTransaction().commit();
			session.close();
			for (Object[] row : leagueObjList) {
				League lg = new League();
				lg.setLeagueName((row[0].toString()));
				lg.setLeagueId((row[1].toString()));
				lg.setTeamName(row[2].toString());
				// overridding to include player points
				lg.setPlayerNo((row[3].toString()));

				lg.setFixturesGeneratedYN(row[4].toString());
				// overridding to include player rank
				lg.setLeagueType((row[5].toString()));

				leagueList.add(lg);
			}

			return leagueList;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return leagueList;
		}
		finally {
			
			factory.close();
		}
	}
	// fetch league standings for a particular league
	@Transactional
		public List<Object[]> fetchStandingsDetails(String leagueId) {
			SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
					.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
					.buildSessionFactory(
					        new StandardServiceRegistryBuilder()
					           
					            //here you apply the custom dataSource
					            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
					            .build());
			Session  session = factory.openSession();
			List<Object[]> leagueObjList = new ArrayList<Object[]>();
			
			try {
				session.beginTransaction();

				ProcedureCall procedureCall = session.createStoredProcedureCall("usp_standingsdetails_populate");
				procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
				procedureCall.registerParameter(2, String.class, ParameterMode.IN);

				procedureCall.setParameter(2, leagueId);

				ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
				ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

				leagueObjList = resultSetOutput.getResultList();

				session.getTransaction().commit();
				session.close();
			

				return leagueObjList;
			} catch (Exception ex) {
				session.getTransaction().rollback();
				session.close();
				return leagueObjList;
			}
			finally {
				
				factory.close();
			}
		}
		// fetch league fixtures and results gameweek wise for a particular league

				public List<Object[]> fetchResultsFixtures(String leagueId) {
					SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
							.addAnnotatedClass(Team.class).addAnnotatedClass(League.class)
							.buildSessionFactory(
							        new StandardServiceRegistryBuilder()
							           
							            //here you apply the custom dataSource
							            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
							            .build());
					Session  session = factory.openSession();
					List<Object[]> leagueObjList = new ArrayList<Object[]>();
					
					try {
						session.beginTransaction();

						ProcedureCall procedureCall = session.createStoredProcedureCall("usp_fixtureresults_populate");
						procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
						procedureCall.registerParameter(2, String.class, ParameterMode.IN);

						procedureCall.setParameter(2, leagueId);

						ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
						ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

						leagueObjList = resultSetOutput.getResultList();

						session.getTransaction().commit();
						session.close();
					

						return leagueObjList;
					} catch (Exception ex) {
						session.getTransaction().rollback();
						session.close();
						return leagueObjList;
					}
finally {
	
	factory.close();
}
				}
				
				
	//fetch premier league fixture for current gameweek

				
				public List<PremierLeagueFixtures> getPlFixturesList(String gameWeek){
					SessionFactory factory = new Configuration()
						
							.buildSessionFactory(
							        new StandardServiceRegistryBuilder()
							           
							            //here you apply the custom dataSource
							            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
							            .build());
					Session  session = factory.openSession();
					List<Object[]> leagueObjList = new ArrayList<Object[]>();
					List<PremierLeagueFixtures> PremierLeagueFixtures = new ArrayList<PremierLeagueFixtures>();	
					try {
						session.beginTransaction();

						ProcedureCall procedureCall = session.createStoredProcedureCall("usp_get_premierleaguefixtureslist");
						procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
						procedureCall.registerParameter(2, String.class, ParameterMode.IN);

						procedureCall.setParameter(2, gameWeek);

						ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
						ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

						leagueObjList = resultSetOutput.getResultList();
						for(int i =0;i<leagueObjList.size();i++) {
							
							PremierLeagueFixtures obj= 
									new PremierLeagueFixtures
									((leagueObjList.get(i))[0].toString(),(leagueObjList.get(i))[1].toString(),null);
							
							PremierLeagueFixtures.add(obj);
						}

						session.getTransaction().commit();
						session.close();
						

						return PremierLeagueFixtures;
					} catch (Exception ex) {
						session.getTransaction().rollback();
						session.close();
						return PremierLeagueFixtures;
					}
finally {
	
	factory.close();
}
				}
				
}
