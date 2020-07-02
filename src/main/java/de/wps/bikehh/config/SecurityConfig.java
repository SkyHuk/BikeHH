package de.wps.bikehh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.wps.bikehh.benutzerverwaltung.security.OAuthEntryPoint;
import de.wps.bikehh.benutzerverwaltung.security.OAuthFilter;
import de.wps.bikehh.benutzerverwaltung.security.OAuthProvider;
import de.wps.bikehh.benutzerverwaltung.service.PasswordEncoderService;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Order(1)
	@Configuration
	public static class RestConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		OAuthProvider authProvider;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**").cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated()
					.and().httpBasic().authenticationEntryPoint(new OAuthEntryPoint()).and()
					.addFilterBefore(new OAuthFilter(), BasicAuthenticationFilter.class).sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(HttpMethod.POST, "/api/user", "/api/auth", "/umfrage-erstellen")
					.antMatchers("/api/password", "api/verify").antMatchers(HttpMethod.DELETE, "/umfragen/delete/*");
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authProvider);
		}
	}

	@Order(2)
	@Configuration
	public static class WebConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		UserDetailService userDetailService;

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailService).passwordEncoder(new PasswordEncoderService());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			String[] publicUrls = { "/", "/logout", "/img/**", "/generated/css/*", "/generated/js/*",
					"/generated/webfonts/*", "/h2/**" };

			http.authorizeRequests().antMatchers(publicUrls).permitAll().anyRequest().authenticated().and().formLogin()
					.loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/login?logout")
					.logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
					.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();

			http.csrf().ignoringAntMatchers("/h2/**");
			http.headers().frameOptions().sameOrigin();
		}
	}
}