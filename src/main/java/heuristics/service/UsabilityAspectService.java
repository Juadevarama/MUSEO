package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.UsabilityAspect;
import heuristics.repository.UsabilityAspectRepository;

@Service
public class UsabilityAspectService {

    @Autowired
    private UsabilityAspectRepository usabilityAspectRepository;

    @Transactional(readOnly = true)
    public List<UsabilityAspect> findAllUsabilityAspect(){
        return usabilityAspectRepository.findAll();
    }
    
}
