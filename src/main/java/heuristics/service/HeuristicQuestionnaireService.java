package heuristics.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicQuestionnaireRepository;

@Service
public class HeuristicQuestionnaireService {

    @Autowired
    private HeuristicQuestionnaireRepository heuristicQuestionnaireRepository;

    @Autowired
    private FinalHeuristicService finalHeuristicService;

    @Transactional(readOnly = true)
    public List<HeuristicQuestionnaire> findAllHeuristicQuestionnaire(){
        return heuristicQuestionnaireRepository.findAll();
    }

    @Transactional
    public void generate(Questionnaire questionnaire, Integer finalHeuristicID){
        
        HeuristicQuestionnaire res = new HeuristicQuestionnaire();

        res.setQuestionnaireID(questionnaire.getId());
        res.setFinalHeuristicID(finalHeuristicID);
        res.setAutomatic(true);
        res.setSelected(false);

        this.heuristicQuestionnaireRepository.save(res);
    } 

    @Transactional(readOnly = true)
    public List<HeuristicQuestionnaire> findHeuristicQuestionnaireByQuestionnaireId(int id) {
        return heuristicQuestionnaireRepository.findHeuristicQuestionnaireByQuestionnaireId(id);     
    }
    
}
