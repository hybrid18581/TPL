package com.rain.tpl.dao;

import java.util.ArrayList;
import java.util.List;

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

import com.rain.tpl.model.League;
import com.rain.tpl.model.Player;
import com.rain.tpl.model.Prediction;
import com.rain.tpl.model.Team;
import com.rain.utility.DataBaseUtility;

public class PredictionDAO {


	
	
	
	
	// save the predictions

	public boolean savePredcitions(Prediction prObj) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class).addAnnotatedClass(Prediction.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		
		Session session = factory.openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(prObj);
			session.getTransaction().commit();
			session.close();

			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		}
		finally
		{factory.close();
			session.close();
		}
	}

	// fetch league List to send prediction
	@SuppressWarnings("unchecked")
	public List<Prediction> getPredictionLeagueList(String ownnerKey, String gameWeek) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Player.class)
				.addAnnotatedClass(Team.class).addAnnotatedClass(League.class).addAnnotatedClass(Prediction.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		
		Session session = factory.openSession();
		List<Object[]> leagueObjList = new ArrayList<Object[]>();
		List<Prediction> leagueList = new ArrayList<Prediction>();

		try {
			session.beginTransaction();

			ProcedureCall procedureCall = session.createStoredProcedureCall("usp_get_PredictionLeagueList");
			procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
			procedureCall.registerParameter(2, String.class, ParameterMode.IN);
			procedureCall.registerParameter(3, String.class, ParameterMode.IN);

			procedureCall.setParameter(2, ownnerKey);
			procedureCall.setParameter(3, gameWeek);
			
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			leagueObjList = resultSetOutput.getResultList();

			session.getTransaction().commit();
			session.close();
			for (Object[] row : leagueObjList) {
				Prediction lg = new Prediction();
				lg.setLeagueName((row[0].toString()));
				lg.setTeamName(row[1].toString());
				lg.setLeagueId((row[2].toString()));
				lg.setTeamId((row[3].toString()));
				lg.setPredictionSent((row[4].toString()));
				
				lg.setPredictionType((row[5])==null?"":(row[5].toString()));
				lg.setPredictionTeam((row[6])==null?"":(row[6].toString()));
				lg.setPredictionScorer((row[7])==null?"":(row[7].toString()));
				lg.setPredictionScoreline((row[8])==null?"":(row[8].toString()));
				lg.setOpponent((row[9])==null?"":(row[9].toString()));
				leagueList.add(lg);
			}

			return leagueList;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return leagueList;
		}
		finally
		{factory.close();
			session.close();
		}
	}
	
	
}
