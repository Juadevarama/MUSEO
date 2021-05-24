package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicPurpose;
import heuristics.model.Purpose;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicPurposeRepository;

@Service
public class HeuristicPurposeService {

    @Autowired
    private HeuristicPurposeRepository heuristicPurposeRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    PurposeService purposeService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicPurpose> findAllHeuristicPurpose(){
        return heuristicPurposeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithPurposes(Questionnaire questionnaire, List<Purpose> choosenPurposes){

        for (HeuristicPurpose heuristicPurpose : heuristicPurposeRepository.findAll()) {
            if(choosenPurposes.contains(purposeService.findPurposeById(heuristicPurpose.getPurposeID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicPurpose.getFinalHeuristicID());
            }
        }
    } 
    
}
