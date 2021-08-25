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
import heuristics.model.GameAspect;
import heuristics.model.HeuristicUser;
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

    @Autowired
    private HeuristicUserService heuristicUserService;

    @Transactional(readOnly = true)
    public List<Questionnaire> findAllQuestionnaire(){
        return questionnaireRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Questionnaire findQuestionnaireByID(Integer id) {
        return questionnaireRepository.findById(id).orElseThrow();
    }

    // Este método saca todos los cuestionarios a partir de un usuario

    @Transactional(readOnly = true)
    public List<Questionnaire> findQuestionnairesByUserID(Integer id){

        List<Questionnaire> ans = new ArrayList<>(); // Creamos la lista a devolver

        for (HeuristicUser heuristicUser : heuristicUserService.findHeuristicUsersByUserId(id)) { // Recorremos todos los objetos heuristicUser del usuario de entrada
            
            Questionnaire questionnaire = findQuestionnaireByID(heuristicUser.getQuestionnaireID()); // Para cada uno, sacamos el cuestionario
            if(!ans.contains(questionnaire)){ // Y vemos si está en la lista que vamos a devolver
                ans.add(questionnaire); // Si no está, lo añadimos
            }
        }

        return ans;
    } 

    @Transactional
    public void saveQuestionnaire(Questionnaire questionnaire) throws DataAccessException{

        if(questionnaire.getClosed() == null){  // Ponemos la propiedad closed a falsa
            questionnaire.setClosed(Boolean.FALSE);
        }

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
    public List<Questionnaire> findQuestionnairesByEvaluator(User evaluator){

        List<Questionnaire> res = new ArrayList<>();

        // Sacamos todas las respuestas del evaluator

        List<Answer> answers = answerService.findAnswersByUserId(evaluator.getId());

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
