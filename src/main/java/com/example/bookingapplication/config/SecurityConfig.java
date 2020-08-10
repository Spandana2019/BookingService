package com.example.bookingapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String encoded = passwordEncoder().encode("pass");

		auth.inMemoryAuthentication().withUser("test").password(encoded).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    //http.cors().and().authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		http.cors();
		http.authorizeRequests().antMatchers("/", "/create", "/list", "/signup","/signin", "/h2/**").permitAll();
		http.authorizeRequests().antMatchers("/api/**").hasRole("USER").and().httpBasic();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
