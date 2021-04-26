package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Questionnaire;
import heuristics.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRawRepository;

    @Transactional(readOnly = true)
    public List<Questionnaire> findAllQuestionnaire(){
        return questionnaireRawRepository.findAll();
    }
    
}
