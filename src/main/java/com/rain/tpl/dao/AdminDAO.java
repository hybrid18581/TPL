package com.rain.tpl.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.json.JSONArray;
import org.json.JSONObject;

import com.rain.tpl.model.PremierLeagueFixtures;
import com.rain.tpl.model.PremierLeagueResults;
import com.rain.tpl.model.PremierLeagueScoreline;
import com.rain.tpl.model.PremierLeagueScorer;
import com.rain.utility.DataBaseUtility;

public class AdminDAO {

	public boolean savePremierLeagueScores(String gameWeek) {

		SessionFactory factory = new Configuration().addAnnotatedClass(PremierLeagueResults.class)
				.addAnnotatedClass(PremierLeagueScoreline.class).addAnnotatedClass(PremierLeagueScorer.class)
				.buildSessionFactory(new StandardServiceRegistryBuilder()

						// here you apply the custom dataSource
						.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();

		try {

			String urls = "http://score-engine.herokuapp.com/api/v1/get-scores-by-gameweek?gameweek=" + gameWeek;
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			session.beginTransaction();
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				JSONObject obj = new JSONObject(output);
				// Object dataas = obj.get("fixtures");
				JSONArray fixtureObj = obj.getJSONArray("data");

				for (int i = 0; i < fixtureObj.length(); i++) {

					Integer homeTeamId = (Integer) fixtureObj.getJSONObject(i).getJSONObject("home").get("id");
					Integer awayTeamId = (Integer) fixtureObj.getJSONObject(i).getJSONObject("away").get("id");

					String homeTeamGoals = (String) fixtureObj.getJSONObject(i).getJSONObject("home").get("goals");

					String awayTeamGoals = (String) fixtureObj.getJSONObject(i).getJSONObject("away").get("goals");

					String homeResult, awayResult = "";

					if (Integer.parseInt(homeTeamGoals) > Integer.parseInt(awayTeamGoals)) {
						homeResult = "won";
						awayResult = "lost";
					} else if (Integer.parseInt(awayTeamGoals) > Integer.parseInt(homeTeamGoals)) {
						awayResult = "won";
						homeResult = "lost";
					} else {
						awayResult = "draw";
						homeResult = "draw";
					}
					// just the results part
					PremierLeagueResults plResultsHomeTeam = new PremierLeagueResults(homeTeamId.toString(), homeResult,
							gameWeek, awayTeamId.toString(), homeTeamGoals.toString());
					PremierLeagueResults plResultsAwayTeam = new PremierLeagueResults(awayTeamId.toString(), awayResult,
							gameWeek, homeTeamId.toString(), awayTeamGoals.toString());
					session.saveOrUpdate(plResultsHomeTeam);
					session.saveOrUpdate(plResultsAwayTeam);
					// save the scorer

					JSONArray homeScorerArray = (JSONArray) fixtureObj.getJSONObject(i).getJSONObject("home")
							.get("scorer");

					if (homeScorerArray.length() != 0) {
						for (int j = 0; j < homeScorerArray.length(); j++) {
							Integer scorerHome = (Integer) homeScorerArray.getJSONObject(j).get("id");
							PremierLeagueScorer homeScorerObj = new PremierLeagueScorer(homeTeamId.toString(),
									scorerHome.toString(), gameWeek, awayTeamId.toString());
							session.saveOrUpdate(homeScorerObj);
						}
					}

					JSONArray awayScorerArray = (JSONArray) fixtureObj.getJSONObject(i).getJSONObject("away")
							.get("scorer");
					if (awayScorerArray.length() != 0) {
						for (int j = 0; j < awayScorerArray.length(); j++) {
							Integer scorerAway = (Integer) awayScorerArray.getJSONObject(j).get("id");
							PremierLeagueScorer awayScorerObj = new PremierLeagueScorer(awayTeamId.toString(),
									scorerAway.toString(), gameWeek, homeTeamId.toString());
							session.saveOrUpdate(awayScorerObj);

						}
					}

					System.out.println();
				}

				session.getTransaction().commit();
				session.close();
				
			}

			conn.disconnect();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			factory.close();
			ex.getMessage();
			return false;
		}

		finally {
			factory.close();
		}

		

	}

	public boolean savePremierLeagueFixtures(String gameWeek) {

		SessionFactory factory = new Configuration().addAnnotatedClass(PremierLeagueFixtures.class)
				.buildSessionFactory(new StandardServiceRegistryBuilder()

						// here you apply the custom dataSource
						.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();

		try {

			String urls = "http://score-engine.herokuapp.com/api/v1/get-fixture-by-gameweek?gameweek=" + gameWeek;
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			session.beginTransaction();
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				JSONObject obj = new JSONObject(output);
				// Object dataas = obj.get("fixtures");
				JSONArray fixtureObj = ((JSONObject) obj.get("data")).getJSONArray("fixtures");
				for (int i = 0; i < fixtureObj.length(); i++) {

					Integer homeTeamId = (Integer) fixtureObj.getJSONObject(i).getJSONObject("home").get("id");
					Integer awayTeamId = (Integer) fixtureObj.getJSONObject(i).getJSONObject("away").get("id");
					PremierLeagueFixtures fixture = new PremierLeagueFixtures(homeTeamId.toString(),
							awayTeamId.toString(), gameWeek);

					session.saveOrUpdate(fixture);

					System.out.println();
				}
				session.getTransaction().commit();
				session.close();
				
			}

			conn.disconnect();
			return true;

		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			factory.close();
			ex.getMessage();
			return false;
		}

		finally {
			factory.close();
		}

		

	}

	public boolean getData() {
		// SessionFactory factory = new
		// Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		boolean result = false;
		try {

			URL url = new URL("http://score-engine.herokuapp.com/api/v1/get-teams");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				// System.out.println(output);
				// caling the save teams data function to save teams
				 result=insertApiDataToDatabase(output);
			}
			
			conn.disconnect();
			return result;
		} catch (Exception ex) {
			
			ex.getMessage();
			return false;
		}
		

	}

	public static boolean insertApiDataToDatabase(String output) {

		SessionFactory factory = new Configuration().buildSessionFactory(new StandardServiceRegistryBuilder()

				// here you apply the custom dataSource
				.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();

		JSONObject obj = new JSONObject(output);
		int code = 0;
		boolean result=false;
		// String n = obj.getString("_links");
		// get the first result
		for (int i = 0; i < 20; i++) {
			JSONObject res = obj.getJSONArray("teams").getJSONObject(i);
			if (res.isNull("id")) {
				// code = ""+i;
			} else {
				code = res.getInt("id");
			}
			String teamCode = Integer.toString(code);
			String name = res.getString("name");
			String link = "";
			// System.out.println(i + "." + code + "-" + name);

			URL url;
			try {
				String playerDataUrl = "http://score-engine.herokuapp.com/api/v1/get-team-details?teamId=" + teamCode;
				url = new URL(playerDataUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				while ((output = br.readLine()) != null) {
					System.out.println(teamCode);
					// caling the save teams data function to save teams
					result=playerData(output, teamCode);
				}

				conn.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
				
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			String playerData;
			System.out.println("Output from Server .... \n");

			try {
				session.beginTransaction();

				StoredProcedureQuery query = session.createStoredProcedureQuery("usp_premierleague_teamdata_save")
						.registerStoredProcedureParameter("code", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("name", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("link", String.class, ParameterMode.IN)
						.setParameter("code", teamCode).setParameter("name", name).setParameter("link", link);
				query.execute();

				// Long commentCount = (Long) query.getOutputParameterValue(1);

				session.getTransaction().commit();
				
				
			} catch (Exception ex) {
				session.getTransaction().rollback();
				session.close();

				return false;
			} finally {
				// factory.close();
			}

		}
		session.close();
		return true;
		
	}

	public static boolean playerData(String playerData, String clubId) {
// no sqauds for 278 and 279 and 286 287 ids
		SessionFactory factory = new Configuration().buildSessionFactory(new StandardServiceRegistryBuilder()

				// here you apply the custom dataSource
				.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();
		JSONObject obj = new JSONObject(playerData);
		// int size= obj.length();

		// String n = obj.getString("_links");
		// get the first result
		Object dataas = obj.get("data");
		int size = ((JSONObject) dataas).getJSONArray("squad").length();

		for (int i = 0; i < size; i++) {
			JSONObject res = ((JSONObject) dataas).getJSONArray("squad").getJSONObject(i);
			// if( res.getString("role").equalsIgnoreCase("player"))
			// {

			String name = res.getString("name");
			int id = res.getInt("id");
			System.out.println(i + "." + clubId + "-" + name);

			try {
				session.beginTransaction();

				StoredProcedureQuery query = session.createStoredProcedureQuery("usp_premierleague_teamplayerdata_save")
						.registerStoredProcedureParameter("code", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("name", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("id", int.class, ParameterMode.IN)

						.setParameter("code", clubId).setParameter("name", name).setParameter("id", id);
				query.execute();

				// Long commentCount = (Long) query.getOutputParameterValue(1);

				session.getTransaction().commit();
				
				// return true;
			} catch (Exception ex) {
				session.getTransaction().rollback();
				session.close();
				factory.close();
				return false;
			} finally {

			}
			// }
		}
		factory.close();
		return true;
	}

	public boolean generateResults() {
		SessionFactory factory = new Configuration().buildSessionFactory(new StandardServiceRegistryBuilder()

				// here you apply the custom dataSource
				.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();

		try {
			session.beginTransaction();

			StoredProcedureQuery query = session.createStoredProcedureQuery("usp_predictoinresults_save");

			query.execute();

			// Long commentCount = (Long) query.getOutputParameterValue(1);

			session.getTransaction().commit();
			session.close();
			// return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		} finally {
			factory.close();
		}

		return true;

	}

	public boolean lockGameweek() {
		SessionFactory factory = new Configuration().buildSessionFactory(new StandardServiceRegistryBuilder()

				// here you apply the custom dataSource
				.applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource()).build());
		Session session = factory.openSession();

		try {

			session.beginTransaction();
			// SELECT last_value FROM gameweek
			session.createNativeQuery("update tpl_admincontrols_tbl set tpl_admincontrols__lockgameweek='Y'")
					.executeUpdate();
			session.getTransaction().commit();
			session.close();
		}

		catch (Exception ed) {
			session.getTransaction().commit();
			session.close();
			return false;
		}

		finally {
			factory.close();
		}

		return true;

	}

}
