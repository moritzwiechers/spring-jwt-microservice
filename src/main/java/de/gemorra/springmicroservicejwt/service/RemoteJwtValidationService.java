package de.gemorra.springmicroservicejwt.service;

import de.gemorra.springmicroservicejwt.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;


@Service
public class RemoteJwtValidationService {
    public UserDetails getUserDetailsFromRemoteJwtValidation(String requestTokenHeader) {

        UserDto userDto = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.AUTHORIZATION, requestTokenHeader)
                .build()
                .get()
                .uri("/validate")
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        return new User(userDto.getUsername(), "dummy", userDto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
