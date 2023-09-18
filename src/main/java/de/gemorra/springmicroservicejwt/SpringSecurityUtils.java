package de.gemorra.springmicroservicejwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class SpringSecurityUtils {

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return "anonym";
    }

    public static List<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return ((UserDetails) authentication.getPrincipal()).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }
        return List.of();
    }

    public static String getUserInformation() {
        return "[User: " + SpringSecurityUtils.getUsername() + " Authorities: " + String.join(",", SpringSecurityUtils.getAuthorities()) + "]";
    }
}
