package heuristics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.User;
import heuristics.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Integer id){
        return userRepository.findById(id).orElseThrow();
    }
    
}
