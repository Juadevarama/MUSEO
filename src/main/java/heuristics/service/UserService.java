package heuristics.service;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;

import heuristics.model.User;

public interface UserService extends UserDetailsService {

    static void saveUser(@Valid User user) {
    }
   
}
