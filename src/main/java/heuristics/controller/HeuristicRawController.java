package heuristics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import heuristics.model.HeuristicRaw;
import heuristics.service.HeuristicRawService;

@Controller
public class HeuristicRawController {

    private final HeuristicRawService heuristicRawService;

    @Autowired
    public HeuristicRawController(HeuristicRawService heuristicRawService){
        this.heuristicRawService = heuristicRawService;
    }

    public List<HeuristicRaw> findAllHeuristicRaw(){
        return heuristicRawService.findAllHeuristicRaw();
    }

    
}
