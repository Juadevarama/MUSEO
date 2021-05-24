package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicUsabilityAspect;
import heuristics.model.UsabilityAspect;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicUsabilityAspectRepository;

@Service
public class HeuristicUsabilityAspectService {

    @Autowired
    private HeuristicUsabilityAspectRepository heuristicUsabilityAspectRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    UsabilityAspectService usabilityAspectService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicUsabilityAspect> findAllHeuristicUsabilityAspect(){
        return heuristicUsabilityAspectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithUsabilityAspects(Questionnaire questionnaire, List<UsabilityAspect> choosenUsabilityAspects){

        for (HeuristicUsabilityAspect heuristicUsabilityAspect : heuristicUsabilityAspectRepository.findAll()) {
            if(choosenUsabilityAspects.contains(usabilityAspectService.findUsabilityAspectById(heuristicUsabilityAspect.getUsabilityAspectID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicUsabilityAspect.getFinalHeuristicID());
            }
        }
    } 
    
}
