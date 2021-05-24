package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicNielsenHeuristic;
import heuristics.model.NielsenHeuristic;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicNielsenHeuristicRepository;

@Service
public class HeuristicNielsenHeuristicService {

    @Autowired
    private HeuristicNielsenHeuristicRepository heuristicNielsenHeuristicRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    NielsenHeuristicService nielsenHeuristicService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicNielsenHeuristic> findAllHeuristicNielsenHeuristic(){
        return heuristicNielsenHeuristicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithNielsenHeuristics(Questionnaire questionnaire, List<NielsenHeuristic> choosenNielsenHeuristics){

        for (HeuristicNielsenHeuristic heuristicNielsenHeuristic : heuristicNielsenHeuristicRepository.findAll()) {
            if(choosenNielsenHeuristics.contains(nielsenHeuristicService.findNielsenHeuristicById(heuristicNielsenHeuristic.getNielsenHeuristicID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicNielsenHeuristic.getFinalHeuristicID());
            }
        }
    } 
    
}
