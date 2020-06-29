package de.wps.bikehh.benutzerverwaltung.security;


import de.wps.bikehh.benutzerverwaltung.exception.ApiException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthFilter extends OncePerRequestFilter {

    String authorizationHeader = "Authorization";
    String apiKeyHeader = "X-API-Key";
    private String apiKeyValue = "bd8dc5a6-53dc-47a9-ab63-6799ea0d59c3";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(authorizationHeader);
        String apiKey = request.getHeader(apiKeyHeader);

        if (accessToken == null || apiKey == null) {
            ApiException exception = new ApiException(ErrorCode.unauthorized);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(exception.toString());
            return;
        }
        if (accessToken.isEmpty() || !apiKey.equals(apiKeyValue)) {
            ApiException exception = new ApiException(ErrorCode.unauthorized);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(exception.toString());
            return;
        }

        Authentication auth = new OAuthToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
