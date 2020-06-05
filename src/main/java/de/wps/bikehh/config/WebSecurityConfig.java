package de.wps.bikehh.config;

import de.wps.bikehh.benutzerverwaltung.exception.ApiException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.security.AuthProvider;
import de.wps.bikehh.benutzerverwaltung.security.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] publicUrls = {"/", "/logout", "/generated/css/*", "/generated/js/*", "/generated/webfonts/*", "/h2/**"};

        http.authorizeRequests().antMatchers(publicUrls).permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/login?logout").logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();

        http.csrf().ignoringAntMatchers("/h2/**");
        http.headers().frameOptions().sameOrigin();
    }

}

@Configuration
@Order(1)
class ApiWebSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationHandler;

    @Autowired
    AuthProvider authProvider;

    @Value("x-api-key")
    private String principalRequestHeader;

    @Value("${bikehh.api.key}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        APIKeyAuthMiddleware filter = new APIKeyAuthMiddleware(principalRequestHeader);

        //filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        //filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        /*
        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (authentication == null) {
                    return null;
                }

                String principal = (String) authentication.getPrincipal();
                if (!principalRequestValue.equals(principal)) {
                    throw new BadCredentialsException(ErrorCode.unauthorized);
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });

        httpSecurity.antMatcher("/api/**").csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilter(filter).
                authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(((request, response, e) -> {
                    ApiException exception = new ApiException(ErrorCode.unauthorized);

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(exception.toString());
                }));*/

        httpSecurity.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth").permitAll().antMatchers(HttpMethod.POST, "/api/user").permitAll()
        .and().authorizeRequests().antMatchers("/api/**").authenticated().and().addFilterBefore(new AuthenticationFilter(), BasicAuthenticationFilter.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}