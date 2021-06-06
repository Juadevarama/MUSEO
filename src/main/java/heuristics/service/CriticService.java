/* package heuristics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Critic;
import heuristics.model.User;
import heuristics.repository.CriticRepository;

@Service
public class CriticService {

    @Autowired
    private CriticRepository criticRepository;

    @Transactional(readOnly = true)
    public List<Critic> findAllCritic(){
        return criticRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Critic findCriticById(Integer id){
        return criticRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void saveCritic(User user) throws DataAccessException{
        Critic critic = new Critic();
        critic.setUsername(user.getUsername());
        critic.setPassword(user.getPassword());
        critic.setName(user.getName());
        critic.setSurnames(user.getSurnames());
        critic.setCompany(user.getCompany());
        critic.setEmail(user.getEmail());
        criticRepository.save(critic);
    }
    
} */
