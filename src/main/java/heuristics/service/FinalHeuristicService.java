package heuristics.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.FinalHeuristic;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.repository.FinalHeuristicRepository;

@Service
public class FinalHeuristicService {

    @Autowired
    private FinalHeuristicRepository finalHeuristicRepository;

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findAllFinalHeuristic(){
        return finalHeuristicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FinalHeuristic findFinalHeuristicById(Integer id){
        return finalHeuristicRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findFHByQuestionnaire(List<HeuristicQuestionnaire> list){
        
        List<FinalHeuristic> res = new ArrayList<>();

        for (HeuristicQuestionnaire hq : list) {

            // Esto se encarga de que no tengamos FH repetidas en la lista

            FinalHeuristic fh = findFinalHeuristicById(hq.getFinalHeuristicID());
            if(!res.contains(fh)){
                res.add(fh);
            } 
        }

        return res;
    }

    // Estos tres métodos separan las fH de un cuestionario en: Las elegidas, las automáticas y el resto. 

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findSelectedFH(List<HeuristicQuestionnaire> hQList){

        List<FinalHeuristic> res = new ArrayList<>();

        for (HeuristicQuestionnaire hQ : hQList) {
            if(hQ.getSelected().equals(true)){
                res.add(findFinalHeuristicById(hQ.getFinalHeuristicID()));
            }
        }
        
        return res;
    }

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findAutomaticFH(List<HeuristicQuestionnaire> hQList){

        List<FinalHeuristic> res = new ArrayList<>();

        for (HeuristicQuestionnaire hQ : hQList) {
            if(hQ.getAutomatic().equals(true) && hQ.getSelected().equals(false)){
                res.add(findFinalHeuristicById(hQ.getFinalHeuristicID()));
            }
        }
        
        return res;
    }

    @Transactional(readOnly = true)
    public List<FinalHeuristic> findRemainderFH(List<HeuristicQuestionnaire> hQList,
    List<FinalHeuristic> fHSelected, List<FinalHeuristic> fHAutomatic){

        List<FinalHeuristic> res = new ArrayList<>();

        for (FinalHeuristic fH : findAllFinalHeuristic()) {
            if(!(fHSelected.contains(fH) || fHAutomatic.contains(fH))){
                res.add(fH);
            }
        }
    
        return res;
    }
}
