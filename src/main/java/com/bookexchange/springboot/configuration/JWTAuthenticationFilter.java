package com.bookexchange.springboot.configuration;

import com.bookexchange.springboot.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        String jwtToken = null;

        String requestURI = request.getRequestURI();

        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7, token.length());
        }

        // Skip authentication for certain endpoints
        if (requestURI.equals("/api/user/signup") || requestURI.equals("/api/user/login") || requestURI.equals("/api/user/checkUsernameAvailablility")) {
            filterChain.doFilter(request, response);
            return;
        }

        //validate the token

        try {
            jwtTokenProvider.validateToken(jwtToken);

            String userName = jwtTokenProvider.getUsername(jwtToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            Response<String> errorResponse = new Response<>();
            errorResponse.setErrorMessage("Unauthorized user");
            errorResponse.setResponseCode(HttpStatus.UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse));
        }
    }

}
