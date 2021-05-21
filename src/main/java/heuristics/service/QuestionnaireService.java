package heuristics.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.DevelopmentPhase;
import heuristics.model.FinalHeuristic;
import heuristics.model.GameAspect;
import heuristics.model.HeuristicPlatform;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Keyword;
import heuristics.model.NielsenHeuristic;
import heuristics.model.Platform;
import heuristics.model.Purpose;
import heuristics.model.Questionnaire;
import heuristics.model.UsabilityAspect;
import heuristics.repository.QuestionnaireRepository;

@Service
@Transactional
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private HeuristicPlatformService heuristicPlatformService;

    @Transactional(readOnly = true)
    public List<Questionnaire> findAllQuestionnaire(){
        return questionnaireRepository.findAll();
    }

    @Transactional
    public void saveQuestionnaire(Questionnaire questionnaire) throws DataAccessException{
        questionnaireRepository.save(questionnaire);
    }

    @Transactional(readOnly = true)
    public void generateHeuristics(Questionnaire questionnaire, List<Platform> choosenPlatforms, 
    List<Purpose> choosenPurposes, List<DevelopmentPhase> choosenDevelopmentPhases, List<GameAspect> choosenGameAspects, 
    List<Keyword> choosenKeywords, List<NielsenHeuristic> choosenNielsenHeuristics, List<UsabilityAspect> choosenUsabilityAspects){
    
    // Antes tenemos que ir mirando si las listas no son nulas
    if(choosenPlatforms!=null){
        heuristicPlatformService.generateHQwithPlatforms(questionnaire, choosenPlatforms);
    }

    } 
    
}
