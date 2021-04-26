package heuristics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import heuristics.model.HeuristicRaw;
import heuristics.service.HeuristicRawService;

@Controller
public class HeuristicRawController {

    private final HeuristicRawService heuristicRawService;

    @Autowired
    public HeuristicRawController(HeuristicRawService heuristicRawService){
        this.heuristicRawService = heuristicRawService;
    }

    // Prueba: Lista de heurísticas
    @GetMapping("/test1")
    public String findAllHeuristicRaw(Model model){
        model.addAttribute("listHeuristicRaws", heuristicRawService.findAllHeuristicRaw());

        return "test1";
    } 
    
}
