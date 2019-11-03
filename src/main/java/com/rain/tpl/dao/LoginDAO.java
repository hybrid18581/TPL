package com.rain.tpl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.rain.tpl.model.League;
import com.rain.tpl.model.Login;
import com.rain.tpl.model.Player;
import com.rain.utility.DataBaseUtility;

public class LoginDAO {

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public LoginDAO() {

	}

	public boolean saveLogin(Login login) {
		SessionFactory factory = new Configuration()
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session session = factory.getCurrentSession();
		
		
		
		
		
		try {
			session.beginTransaction();
			login.setActiveYn("Y");

			// encrypt the password

			String encodedPassword = passwordEncoder.encode(login.getPassword());
			login.setPassword("{bcrypt}" + encodedPassword);
			session.save(login);
			StoredProcedureQuery query = session.createStoredProcedureQuery("usp_usercredentials_save")
					.registerStoredProcedureParameter("emailid", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("password", String.class, ParameterMode.IN)
					.setParameter("emailid", login.getEmailId()).setParameter("password", login.getPassword());

			query.execute();

			// Long commentCount = (Long) query.getOutputParameterValue(1);

			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return false;
		} finally {

			factory.close();
		}
	}

	public Login fetchUserDetails(String userName) {
		SessionFactory factory = new Configuration().addAnnotatedClass(Login.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		try {
			
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			Login userDetails = (Login) session
					.createQuery("select L from Login L where L.emailId=:userName and L.activeYn='Y'")
					.setParameter("userName", userName).uniqueResult();
			session.getTransaction().commit();
			session.close();
			return userDetails;
		} catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return null;
		} finally {

			factory.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> fetchGameweekdata() {
		List<Object[]> adminData = new ArrayList<Object[]>();
		SessionFactory factory = new Configuration().addAnnotatedClass(Login.class)
				.buildSessionFactory(
				        new StandardServiceRegistryBuilder()
				           
				            //here you apply the custom dataSource
				            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
				            .build());
		Session  session = factory.openSession();
		try {

			// reference query
			// select setval('gameweek',(select last_value from gameweek)+1,false);

			session.beginTransaction();
			//SELECT last_value FROM gameweek
			adminData=(List<Object[]>) session.createNativeQuery(
				    "SELECT tpl_admincontrols__lockgameweek,tpl_admincontrols__gameweek from tpl_admincontrols_tbl" )
					 .getResultList();
					 
			
		
			
			session.getTransaction().commit();
			session.close();
			}
			
			
		 catch (Exception ex) {
			session.getTransaction().rollback();
			session.close();
			return null;
		}
		finally
		{
			
			factory.close();
		}
		return adminData;
	}
}
