package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.DevelopmentPhase;
import heuristics.repository.DevelopmentPhaseRepository;

@Service
public class DevelopmentPhaseService {

    @Autowired
    private DevelopmentPhaseRepository developmentphaseRepository;

    @Transactional(readOnly = true)
    public List<DevelopmentPhase> findAllDevelopmentphase(){
        return developmentphaseRepository.findAll();
    }
    
}
