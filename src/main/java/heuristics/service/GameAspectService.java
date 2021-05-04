package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.GameAspect;
import heuristics.repository.GameAspectRepository;

@Service
public class GameAspectService {

    @Autowired
    private GameAspectRepository gameAspectRepository;

    @Transactional(readOnly = true)
    public List<GameAspect> findAllGameAspect(){
        return gameAspectRepository.findAll();
    }
    
}
