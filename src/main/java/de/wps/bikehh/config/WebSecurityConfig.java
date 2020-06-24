package de.wps.bikehh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] publicUrls = { "/", "/img/logo.png", "/logout", "/generated/css/*", "/generated/js/*",
				"/generated/webfonts/*", "/h2/**" };

		http.authorizeRequests().antMatchers(publicUrls).permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout()
				// .formLogin().loginPage("/login").defaultSuccessUrl("/uebersicht").permitAll().and().logout()
				.logoutSuccessUrl("/login?logout").logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll();
		http.csrf().ignoringAntMatchers("/h2/**");
		http.headers().frameOptions().sameOrigin();
		/*
		 * http.httpBasic(). and() .authorizeRequests().antMatchers(HttpMethod.POST,
		 * "/umfrage-erstellen").hasRole("ADMIN");
		 */
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.POST, "/umfrage-erstellen");
		super.configure(webSecurity);
	}
}