package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicPlatform;
import heuristics.model.Platform;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicPlatformRepository;

@Service
public class HeuristicPlatformService {

    @Autowired
    private HeuristicPlatformRepository heuristicPlatformRepository;

    @Autowired
    HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @Autowired
    PlatformService platformService; 
    
    @Transactional(readOnly = true)
    public List<HeuristicPlatform> findAllHeuristicPlatform(){
        return heuristicPlatformRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void generateFHwithPlatforms(Questionnaire questionnaire, List<Platform> choosenPlatforms){

        for (HeuristicPlatform heuristicPlatform : heuristicPlatformRepository.findAll()) {
            if(choosenPlatforms.contains(platformService.findPlatformById(heuristicPlatform.getPlatformID()))){
    
                heuristicQuestionnaireService.generate(questionnaire, heuristicPlatform.getFinalHeuristicID());
            }
        }
    } 
    
}
