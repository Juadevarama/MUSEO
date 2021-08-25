package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicKeyword;
import heuristics.model.Keyword;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicKeywordRepository;

@Service
public class HeuristicKeywordService {

    @Autowired
    private HeuristicKeywordRepository heuristicKeywordRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    KeywordService keywordService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicKeyword> findAllHeuristicKeyword(){
        return heuristicKeywordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateHQwithKeywords(Questionnaire questionnaire, List<Keyword> choosenKeywords){

        for (HeuristicKeyword heuristicKeyword : heuristicKeywordRepository.findAll()) {
            if(choosenKeywords.contains(keywordService.findKeywordById(heuristicKeyword.getKeywordID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicKeyword.getFinalHeuristicID());
            }
        }
    } 
    
}
