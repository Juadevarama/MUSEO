package heuristics.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Answer;
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
import heuristics.model.User;
import heuristics.repository.QuestionnaireRepository;

@Service
@Transactional
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HeuristicPlatformService heuristicPlatformService;

    @Autowired
    private HeuristicPurposeService heuristicPurposeService;

    @Autowired
    private HeuristicDevelopmentPhaseService heuristicDevelopmentPhaseService;

    @Autowired
    private HeuristicGameAspectService heuristicGameAspectService;

    @Autowired
    private HeuristicKeywordService heuristicKeywordService;

    @Autowired
    private HeuristicNielsenHeuristicService heuristicNielsenHeuristicService;

    @Autowired
    private HeuristicUsabilityAspectService heuristicUsabilityAspectService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private HeuristicQuestionnaireService heuristicQuestionnaireService;

    @Transactional(readOnly = true)
    public List<Questionnaire> findAllQuestionnaire(){
        return questionnaireRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Questionnaire findQuestionnaireByID(Integer id) {
        return questionnaireRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Questionnaire> findQuestionnairesByUserID(Integer id){
        return questionnaireRepository.findQuestionnairesByUserID(id);
    }

    @Transactional
    public void saveQuestionnaire(Questionnaire questionnaire) throws DataAccessException{

        if((questionnaire.getFilled() == null) && (questionnaire.getClosed() == null)){
            questionnaire.setFilled(false);
            questionnaire.setClosed(false);
        }
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        questionnaire.setUserID(userService.findUserByUsername(userDetails.getUsername()).getId());

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

        if(choosenPurposes!=null){
            heuristicPurposeService.generateHQwithPurposes(questionnaire, choosenPurposes);
        }

        if(choosenDevelopmentPhases!=null){
            heuristicDevelopmentPhaseService.generateHQwithDevelopmentPhases(questionnaire, choosenDevelopmentPhases);
        }

        if(choosenGameAspects!=null){
            heuristicGameAspectService.generateHQwithGameAspects(questionnaire, choosenGameAspects);
        }

        if(choosenKeywords!=null){
            heuristicKeywordService.generateHQwithKeywords(questionnaire, choosenKeywords);
        }

        if(choosenNielsenHeuristics!=null){
            heuristicNielsenHeuristicService.generateHQwithNielsenHeuristics(questionnaire, choosenNielsenHeuristics);
        }

        if(choosenUsabilityAspects!=null){
            heuristicUsabilityAspectService.generateHQwithUsabilityAspects(questionnaire, choosenUsabilityAspects);
        }

    } 

    @Transactional(readOnly = true)
    public List<Questionnaire> findQuestionnairesByCritic(User critic){

        List<Questionnaire> res = new ArrayList<>();

        // Sacamos todas las respuestas del critic

        List<Answer> answers = answerService.findAnswersByUserId(critic.getId());

        for (Answer ans : answers) {

            /* Las vamos recorriendo, vamos sacando todos los objetos HQ, y miramos si el cuestionario de estos
            se encuentra en la lista. Si no está, lo añadimos a la lista*/

            Questionnaire ansQuestionnaire = findQuestionnaireByID(heuristicQuestionnaireService.
            findHeuristicQuestionnaireById(ans.getHeuristicQuestionnaireID()).getQuestionnaireID());

            if(!(res.contains(ansQuestionnaire))){
                res.add(ansQuestionnaire);
            }
        }

        return res;
    } 
    
}
