package heuristics.service;


import java.util.List;

import org.springframework.stereotype.Service;

import heuristics.model.HeuristicRaw;
import heuristics.repository.HeuristicRawRepository;

@Service
public class HeuristicRawService {

    private HeuristicRawRepository heuristicRawRepository;

    public List<HeuristicRaw> findAllHeuristicRaw(){
        return (List<HeuristicRaw>) heuristicRawRepository.findAll();
    }
    
}
