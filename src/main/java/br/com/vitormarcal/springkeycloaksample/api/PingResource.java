package br.com.vitormarcal.springkeycloaksample.api;


import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.vitormarcal.springkeycloaksample.api.Roles.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/ping")
public class PingResource {




    @GetMapping
    public String all() {
        return "Todos acessam";
    }

    @Secured({ROLE_ADMIN})
    @GetMapping("/admins")
    public String onlyAdmins() {
        return "Eu sou um admin";
    }

    @Secured({ROLE_WRITER})
    @GetMapping("/writers")
    public String onlyWriters() {
        return "Eu sou um writer";
    }

    @Secured({ROLE_READER})
    @GetMapping("/readers")
    public String onlyReaders() {
        return "Eu sou um reader";
    }


}