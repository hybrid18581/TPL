package com.rain.tpl.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.view.InternalResourceViewResolver;


import com.rain.tpl.SendPredictionController;
import com.rain.utility.DataBaseUtility;
import com.rain.validator.PassMatchValidator;
import com.rain.validator.SendPredictionValidator;



@Configuration

@PropertySource({ "classpath:persistence-mysql.properties" }) // files stored in resources are automatically copied in
																// the class path while executing
@ComponentScan(basePackages = "com.rain.tpl")
// spring configuration file that replaces the xml config
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// define a bean for ViewResolver

	@Bean
	public DataSource myDataSource() {
		
		
        return DataBaseUtility.getDataSource(); 
	}
	
	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;				
	}

	
	// need a helper method 
	// read environment property and convert to int
	
	private int getIntProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		
		// now convert to int
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}	
	
	
	
	
	
	// define a bean for view resolver
	/*
	 * @Bean public ViewResolver viewResolver() { InternalResourceViewResolver
	 * viewResolver = new InternalResourceViewResolver();
	 * viewResolver.setPrefix("/WEB-INF/views/"); viewResolver.setSuffix(".jsp");
	 * 
	 * return viewResolver; }
	 */
		
		 
		  
		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry) {

			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		 
		}
	@Bean
	public SendPredictionValidator predictionValidation() {
		
		SendPredictionValidator predictionValidationObj = new SendPredictionValidator();
		
		return predictionValidationObj;
	}
	
	@Bean
	public PassMatchValidator passMatchValidator() {
		
		PassMatchValidator passMatchValidatorObj = new PassMatchValidator();
		
		return passMatchValidatorObj;
	}
}
