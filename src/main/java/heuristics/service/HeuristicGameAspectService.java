package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicGameAspect;
import heuristics.model.GameAspect;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicGameAspectRepository;

@Service
public class HeuristicGameAspectService {

    @Autowired
    private HeuristicGameAspectRepository heuristicGameAspectRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    GameAspectService gameAspectService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicGameAspect> findAllHeuristicGameAspect(){
        return heuristicGameAspectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithGameAspects(Questionnaire questionnaire, List<GameAspect> choosenGameAspects){

        for (HeuristicGameAspect heuristicGameAspect : heuristicGameAspectRepository.findAll()) {
            if(choosenGameAspects.contains(gameAspectService.findGameAspectById(heuristicGameAspect.getGameAspectID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicGameAspect.getFinalHeuristicID());
            }
        }
    } 
    
}
