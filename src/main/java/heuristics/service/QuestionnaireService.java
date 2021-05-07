package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.DevelopmentPhase;
import heuristics.model.GameAspect;
import heuristics.model.Keyword;
import heuristics.model.NielsenHeuristic;
import heuristics.model.Platform;
import heuristics.model.Purpose;
import heuristics.model.Questionnaire;
import heuristics.model.UsabilityAspect;
import heuristics.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Transactional(readOnly = true)
    public List<Questionnaire> findAllQuestionnaire(){
        return questionnaireRepository.findAll();
    }

    @Transactional(readOnly = true)
    private Questionnaire generateQuestionnaire(List<Platform> choosenPlatforms, List<Purpose> choosenPurposes,
    List<DevelopmentPhase> choosenDevelopmentPhases, List<GameAspect> choosenGameAspects, List<Keyword> choosenKeywords,
    List<NielsenHeuristic> choosenNielsenHeuristics, List<UsabilityAspect> choosenUsabilityAspects){
        return null;

    }
    
}
