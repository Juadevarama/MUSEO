package heuristics.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicUser;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.Questionnaire;
import heuristics.model.User;
import heuristics.repository.HeuristicUserRepository;

@Service
public class HeuristicUserService {

    @Autowired
    private HeuristicUserRepository heuristicUserRepository;

    @Autowired
    private UserServiceImpl userService;

    @Transactional(readOnly = true)
    public List<HeuristicUser> findAllHeuristicUser(){
        return heuristicUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<HeuristicUser> findHeuristicUsersByUserId(Integer id){
        return heuristicUserRepository.findHeuristicUsersByUserID(id);
    }

    @Transactional(readOnly = true)
    public List<HeuristicUser> findHeuristicUserByquestionnaireID(Integer id) {
        return heuristicUserRepository.findHeuristicUsersByquestionnaireID(id);
    } 

    @Transactional
    public void saveHeuristicUser(HeuristicUser hU) throws DataAccessException{
        heuristicUserRepository.save(hU);
    }

    @Transactional
    public void generate(Integer userID, Integer questionnaireID){
        
        HeuristicUser heuristicUser = new HeuristicUser();

        heuristicUser.setQuestionnaireID(questionnaireID);
        heuristicUser.setUserID(userID);
        if(userService.findUserById(userID).getRole().equals("Administrator")){
            heuristicUser.setOwner(Boolean.TRUE);
        }
        heuristicUser.setFilled(Boolean.FALSE);

        saveHeuristicUser(heuristicUser);
          
    } 




  
}
