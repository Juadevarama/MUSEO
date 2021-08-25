package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicDevelopmentPhase;
import heuristics.model.DevelopmentPhase;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicDevelopmentPhaseRepository;

@Service
public class HeuristicDevelopmentPhaseService {

    @Autowired
    private HeuristicDevelopmentPhaseRepository heuristicDevelopmentPhaseRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    DevelopmentPhaseService developmentPhaseService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicDevelopmentPhase> findAllHeuristicDevelopmentPhase(){
        return heuristicDevelopmentPhaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithDevelopmentPhases(Questionnaire questionnaire, List<DevelopmentPhase> choosenDevelopmentPhases){

        for (HeuristicDevelopmentPhase heuristicDevelopmentPhase : heuristicDevelopmentPhaseRepository.findAll()) {
            if(choosenDevelopmentPhases.contains(developmentPhaseService.findDevelopmentPhaseById(heuristicDevelopmentPhase.getDevelopmentPhaseID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicDevelopmentPhase.getFinalHeuristicID());
            }
        }
    } 
    
}
