package de.gemorra.springmicroservicejwt;

import de.gemorra.springmicroservicejwt.service.RemoteJwtValidationService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class RemoteJwtRequestFilter extends OncePerRequestFilter {

    private final RemoteJwtValidationService remoteJwtValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(requestTokenHeader)) {
            UserDetails userDetailsIfValid = remoteJwtValidationService.getUserDetailsFromRemoteJwtValidation(requestTokenHeader);
            if (userDetailsIfValid != null) {
                setSpringSecurityContextData(userDetailsIfValid, request);
            }
        }
        response.addHeader("UserInfo", SpringSecurityUtils.getUserInformation());
        chain.doFilter(request, response);
    }


    private void setSpringSecurityContextData(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
