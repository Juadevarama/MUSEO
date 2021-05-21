package heuristics.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.FinalHeuristic;
import heuristics.model.HeuristicQuestionnaire;
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

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findFHByCuestionnaire(List<HeuristicQuestionnaire> list){
        
        List<FinalHeuristic> res = new ArrayList<>();

        for (HeuristicQuestionnaire hq : list) {

            // Esto se encarga de que no tengamos FH repetidas en la lista

            FinalHeuristic fh = findFinalHeuristicById(hq.getFinalHeuristicID());
            if(!res.contains(fh)){
                res.add(fh);
            } 
        }

        return res;
    }
}
