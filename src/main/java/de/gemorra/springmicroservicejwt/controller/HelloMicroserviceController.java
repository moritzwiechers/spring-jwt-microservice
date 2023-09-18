package de.gemorra.springmicroservicejwt.controller;

import de.gemorra.springmicroservicejwt.SpringSecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloMicroserviceController {

    @GetMapping(value = "/hello-world-microservice")
    public String helloWorldMicroservice(){
        return "Hello World from Microservice " + SpringSecurityUtils.getUsername();
    }

    @GetMapping(value = "/hello-user-microservice")
    public String helloUserMicroservice(){
        return "Hello User from Microservice " + SpringSecurityUtils.getUsername();
    }

    @GetMapping(value = "/hello-admin-microservice")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String helloAdminMicroservice(){
        return "Hello Admin from Microservice " + SpringSecurityUtils.getUsername();
    }
}
