package de.wps.bikehh.config;

import de.wps.bikehh.benutzerverwaltung.security.OAuthEntryPoint;
import de.wps.bikehh.benutzerverwaltung.security.OAuthFilter;
import de.wps.bikehh.benutzerverwaltung.security.OAuthProvider;
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

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
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
    class AuthWebSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        OAuthProvider authProvider;

        //@TODO frontend still not working. Except /h2/**
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
       /*     httpSecurity.antMatcher()
                    antMatchers(HttpMethod.POST, "/api/auth").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/user").permitAll();*/


            httpSecurity.httpBasic().authenticationEntryPoint(new OAuthEntryPoint()).and().authorizeRequests().antMatchers("/api/**").authenticated().and().addFilterBefore(new OAuthFilter(), BasicAuthenticationFilter.class).csrf().disable();
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
            //.csrf().ignoringAntMatchers(
            //            "/api-key-only",
            //            "/dual-auth"
            //    )
            //        ;
        }

        @Override
        public void configure(WebSecurity webSecurity) throws Exception {
            webSecurity.ignoring().antMatchers(HttpMethod.POST, "/api/user", "/api/auth").and().ignoring().antMatchers("/h2/**", "/api/password", "/api/verify", "/generated/css/*", "/generated/js/*", "/generated/webfonts/*", "/login");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authProvider);
        }
    }

  /*  @Configuration
    @Order(2)
    class ApiWebSecurity extends WebSecurityConfigurerAdapter {

        @Value("x-api-key")
        private String principalRequestHeader;

        @Value("${bikehh.api.key}")
        private String principalRequestValue;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            ApiKeyAuth filter = new ApiKeyAuth(principalRequestHeader);

            //filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

            filter.setAuthenticationManager(new AuthenticationManager() {

                @Override
                public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                    System.out.println("api-key");
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
                    authorizeRequests().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(((request, response, e) -> {
                ApiException exception = new ApiException(ErrorCode.unauthorized);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(exception.toString());
            }));

        }


    }*/
}