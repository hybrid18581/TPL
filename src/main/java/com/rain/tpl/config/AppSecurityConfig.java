package com.rain.tpl.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	// add reference to datasource declared in appconfig file
	@Autowired
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*// add users for in memory authentication

		auth.inMemoryAuthentication().withUser("john").password("{noop}pass123").roles("player", "admin");
		auth.inMemoryAuthentication().withUser("soh").password("{noop}pass123").roles("player");
		auth.inMemoryAuthentication().withUser("susan").password("{noop}pass123").roles("admin");*/
		
		//use JDBC authentication 
		
		auth.jdbcAuthentication().dataSource(securityDataSource);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		
		
		 http.authorizeRequests().antMatchers("/images/**","/Register/**"
				 ,"/css/**","/fonts/**","/js/**","/vendor/**","/assets/**").permitAll();
		http.authorizeRequests().antMatchers("/").hasAnyRole("admin", "player").
		antMatchers("/admin").hasRole("admin").
		
		// antMatchers("/").hasRole(role) to be used when there is single role
		 anyRequest().authenticated().
		// to be used when there is no role based criteria to access certain pages
			and().formLogin().loginPage("/showLoginPage")
				.loginProcessingUrl("/authenticateTheUser").
				
				
				permitAll().and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/showLoginPage").
				invalidateHttpSession(true).clearAuthentication(true).and()
				.exceptionHandling().accessDeniedPage("/access-denied");
	}

	
	 
}
