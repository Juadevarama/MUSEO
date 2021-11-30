package heuristics.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.HeuristicUser;
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

    @Transactional(readOnly = true)
    public HeuristicUser findHeuristicUserByIDs(Integer userID, Integer questionnaireID) {
        return heuristicUserRepository.findHeuristicUsersByUserIDAndQuestionnaireID(userID, questionnaireID);
    } 

    @Transactional
    public void saveHeuristicUser(HeuristicUser hU) throws DataAccessException{
        heuristicUserRepository.save(hU);
    }

    @Transactional
    public void generate(Integer userID, Integer questionnaireID){

        /*  Miramos si el usuario ya está en la lista de HeuristicUser de este cuestionario.
            Si no, lo añadimos generando un objeto nuevo. */

        List<HeuristicUser> qList = findHeuristicUserByquestionnaireID(questionnaireID);
        
        if(qList.stream().noneMatch(hU -> hU.getUserID().equals(userID))){
            
            HeuristicUser heuristicUser = new HeuristicUser();

            heuristicUser.setQuestionnaireID(questionnaireID);
            heuristicUser.setUserID(userID);
            if(userService.findUserById(userID).getRole().equals("Administrator")){
                heuristicUser.setOwner(Boolean.TRUE);
            } else {
                heuristicUser.setOwner(Boolean.FALSE);
            }
            heuristicUser.setFilled(Boolean.FALSE);
    
            saveHeuristicUser(heuristicUser);
        }      
    } 




  
}
