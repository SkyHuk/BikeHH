package de.wps.bikehh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] publicUrls = {
				"/",
				"/logout",
				"/generated/css/*",
				"/generated/js/*",
				"/generated/webfonts/*"
		};

		http.authorizeRequests()
				.antMatchers(publicUrls).permitAll().anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().logoutSuccessUrl("/login?logout").logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll();
	}
}