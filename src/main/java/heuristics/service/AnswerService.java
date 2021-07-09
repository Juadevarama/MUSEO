package heuristics.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Answer;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Questionnaire;
import heuristics.model.User;
import heuristics.repository.AnswerRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserServiceImpl userService;

    @Transactional(readOnly = true)
    public List<Answer> findAllAnswer(){
        return answerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Answer> findAnswersByUserId(Integer id){
        return answerRepository.findAnswersByUserID(id);
    }

    @Transactional
    public void saveAnswer(Answer ans) throws DataAccessException{
        answerRepository.save(ans);
    }

    @Transactional
    public void generate(List<HeuristicQuestionnaire> selectedHQ, User recipient){
        
        for (HeuristicQuestionnaire hQ : selectedHQ) {

            Answer answer = new Answer();

            answer.setHeuristicQuestionnaireID(hQ.getId());
            answer.setUserID(recipient.getId());
            answer.setAnsString(null);

            saveAnswer(answer);
        }  
    } 


  
}
