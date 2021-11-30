package heuristics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Login

    @GetMapping("/")
    public String welcome(){
      return "redirect:/main";
    }

    // Main Page
    @GetMapping("/main")
    public String main(){

      return "main";
    }

    
}
