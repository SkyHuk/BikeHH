package de.wps.bikehh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.wps.bikehh.authentifizierung.security.OAuthEntryPoint;
import de.wps.bikehh.authentifizierung.security.OAuthFilter;
import de.wps.bikehh.authentifizierung.security.OAuthProvider;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Wieso weshalb warum:
	// https://docs.spring.io/spring-security/site/docs/3.2.x/reference/htmlsingle/html5/#multiple-httpsecurity

	@Autowired
	private UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Administrator beim Start neu hinzufügen.
		// TODO: Für Produktivbetrieb löschen
		userService.createUser("admin@bikehh.de", "admin_pw", Rollen.ROLE_ADMIN);
	}

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private OAuthProvider authProvider;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**")
					.cors().and().csrf().disable()
					.authorizeRequests().anyRequest().authenticated()
					.and()
					.authenticationProvider(authProvider)
					.addFilterBefore(new OAuthFilter(), BasicAuthenticationFilter.class)
					.httpBasic()
					.authenticationEntryPoint(new OAuthEntryPoint())
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			String[] publicUrls = {
					"/api/auth",
					"/api/verify",
					"/api/password/forgot",
					"/api/password/reset",
			};

			web.ignoring().antMatchers(publicUrls);
		}
	}

	@Configuration
	@Order(2)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Autowired
		private UserService userService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			String[] publicUrls = {
					"/",
					"/logout",
					"/img/**",
					"/generated/css/*",
					"/generated/js/*",
					"/generated/webfonts/*",
					"/h2/**"
			};

			http.authorizeRequests()
					.antMatchers(publicUrls).permitAll()
					.anyRequest().hasAuthority(Rollen.ROLE_ADMIN)
					.and()
					.formLogin()
					.loginPage("/login").permitAll()
					.and()
					.logout().logoutSuccessUrl("/login?logout")
					.logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
					.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();

			http.csrf().ignoringAntMatchers("/h2/**");
			http.headers().frameOptions().sameOrigin();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
					.userDetailsService(userService)
					.passwordEncoder(passwordEncoder);
		}
	}

}