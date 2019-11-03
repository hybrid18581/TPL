package com.rain.tpl.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ParameterMode;

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



public class UtilityDOA {

	
	
	public Map<String,String> getDropdowns(String switchs,String value)
	{	SessionFactory factory = new Configuration()
	.buildSessionFactory(
	        new StandardServiceRegistryBuilder()
	           
	            //here you apply the custom dataSource
	            .applySetting(Environment.DATASOURCE, DataBaseUtility.getDataSource())
	            .build());
	Session session = factory.openSession();
try
{
	
	session.beginTransaction();
	
	List<Object[]> leagueObjList = new ArrayList<Object[]>();
	ProcedureCall procedureCall = session.createStoredProcedureCall("usp_dropdown_listfill");
	procedureCall.registerParameter(1, Class.class, ParameterMode.REF_CURSOR);
	procedureCall.registerParameter(2, String.class, ParameterMode.IN);
	procedureCall.registerParameter(3, String.class, ParameterMode.IN);

	procedureCall.setParameter(2, switchs);
	procedureCall.setParameter(3, value);
	
	ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
	ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

	leagueObjList = resultSetOutput.getResultList();
	//Long commentCount = (Long) query.getOutputParameterValue(1);
		Map<String, String> dropdownList = new LinkedHashMap<String, String>();
	
		for(int i=0;i<leagueObjList.size();i++)
		{
			
			dropdownList.put(leagueObjList.get(i)[0].toString(), leagueObjList.get(i)[1].toString());
		}
		
	
	session.getTransaction().commit();
	session.close();
	
	return dropdownList;
}
catch(Exception ex)
{
	session.getTransaction().rollback();
	session.close();
	return null;
}
finally
{
	
}
	}
}
