package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.FinalHeuristic;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicQuestionnaireRepository;

@Service
public class HeuristicQuestionnaireService {

    @Autowired
    private static HeuristicQuestionnaireRepository heuristicQuestionnaireRepository;

    @Transactional(readOnly = true)
    public List<HeuristicQuestionnaire> findAllHeuristicQuestionnaire(){
        return heuristicQuestionnaireRepository.findAll();
    }

    @Transactional
    public static void generate(Questionnaire questionnaire, FinalHeuristic finalHeuristic){
        
        HeuristicQuestionnaire res = new HeuristicQuestionnaire();

        res.setQuestionnaire(questionnaire);
        res.setFinalHeuristic(finalHeuristic);
        res.setAutomatic(true);
        res.setSelected(false);

        heuristicQuestionnaireRepository.save(res);     
    }

    public HeuristicQuestionnaire findHeuristicQuestionnaireByQuestionnaireId(Integer id) {
        return heuristicQuestionnaireRepository.findHeuristicQuestionnaireByQuestionnaireId(id);
    }
    
}
