package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.FinalHeuristic;
import heuristics.repository.FinalHeuristicRepository;

@Service
public class FinalHeuristicService {

    @Autowired
    private FinalHeuristicRepository finalHeuristicRepository;

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findAllFinalHeuristic(){
        return finalHeuristicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FinalHeuristic findFinalHeuristicById(Integer id){
        return finalHeuristicRepository.findById(id).orElseThrow();
    }
}
