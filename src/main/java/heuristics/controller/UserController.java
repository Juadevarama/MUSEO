package heuristics.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import heuristics.model.User;

import heuristics.service.UserService;
import heuristics.service.UserServiceImpl;


@Controller
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;

    } 

    // Login

    @GetMapping("/login")
    public String initLoginUserForm(Model model, HttpServletRequest request){

        return "login";
    }

    // Logout

    @GetMapping("/logout")
    public String initLogoutUserForm(HttpServletRequest request, HttpServletResponse response){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }    

    // Create User: GET

    @GetMapping("/register")
    public String initCreateUserForm(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    // Create User: POST
    
    @PostMapping("/register")
    public String processCreateUserForm(@Valid User user, Model model) throws DataAccessException{

        userService.saveUser(user);
   
        return "redirect:/register?success";   
    }

    // Show User: GET

    @GetMapping("/user/{userID}")
    public String iniShowUserForm(@PathVariable("userID") int userID, Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //if(userDetails.)
        //model.addAttribute("userID", this.administratorService)

        return null;
    }
    

    // Todavía no podemos porque no hemos puesto la seguridad. Vamos a ello. 

    
    
}
