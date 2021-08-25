package heuristics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.FinalHeuristic;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Questionnaire;
import heuristics.repository.HeuristicQuestionnaireRepository;

@Service
public class HeuristicQuestionnaireService {

    @Autowired
    private HeuristicQuestionnaireRepository heuristicQuestionnaireRepository;

    @Transactional(readOnly = true)
    public List<HeuristicQuestionnaire> findAllHeuristicQuestionnaire(){
        return heuristicQuestionnaireRepository.findAll();
    }

    @Transactional(readOnly = true)
    public HeuristicQuestionnaire findHeuristicQuestionnaireById(Integer id){
        return heuristicQuestionnaireRepository.findHeuristicQuestionnaireById(id);
    }

    @Transactional
    public void saveHeuristicQuestionnaire(HeuristicQuestionnaire hQ) throws DataAccessException{
        heuristicQuestionnaireRepository.save(hQ);
    }


    @Transactional(readOnly = true)
    public List<HeuristicQuestionnaire> findHeuristicQuestionnaireByQuestionnaireId(int id) {
        return heuristicQuestionnaireRepository.findHeuristicQuestionnaireByQuestionnaireId(id);     
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

    @Transactional
    public void updateHQ(List<FinalHeuristic> fHList, Questionnaire questionnaire){

        /*  Recorremos la lista de fH que hemos seleccionado. El objetivo es ver si ya había un objeto hQ que 
            uniera esa fH con el cuestionario, por si no lo hay, crearlo, y si lo hay, ponerle el selected a true.
            (Puede que esté el objeto y no esté selected a true porque sea una fH automática).  */

        List<HeuristicQuestionnaire> hQList = findHeuristicQuestionnaireByQuestionnaireId(questionnaire.getId());

        for (FinalHeuristic fH : fHList) {
            if(hQList.stream().noneMatch(hQ -> hQ.getFinalHeuristicID().equals(fH.getId()))){
                HeuristicQuestionnaire newHQ = new HeuristicQuestionnaire();
                newHQ.setFinalHeuristicID(fH.getId());
                newHQ.setQuestionnaireID(questionnaire.getId());
                newHQ.setAutomatic(false);
                newHQ.setSelected(true);
                saveHeuristicQuestionnaire(newHQ);
            } else {
                hQList.stream().filter(hQ -> hQ.getFinalHeuristicID().equals(fH.getId())).findFirst().get().setSelected(true);
            }
        }
    }

    @Transactional
    public List<HeuristicQuestionnaire> filterSelectedHQ(List<HeuristicQuestionnaire> hQList){

        List<HeuristicQuestionnaire> selectedHQ = new ArrayList<>();

        for (HeuristicQuestionnaire hQ : hQList) {
           if(hQ.getSelected().equals(true)){
               selectedHQ.add(hQ);
           }
        }

        return selectedHQ;
    }
    
}
