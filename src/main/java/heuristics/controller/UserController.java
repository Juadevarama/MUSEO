package heuristics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import heuristics.model.User;
import heuristics.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // Prueba: Lista todos los de usuarios
    @GetMapping("/allUser")
    public String findAllUser(Model model){
        model.addAttribute("userList", userService.findAllUser());
        return "test2";
    } 
    
}
