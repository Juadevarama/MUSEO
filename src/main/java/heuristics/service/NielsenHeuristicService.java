package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.NielsenHeuristic;
import heuristics.repository.NielsenHeuristicRepository;

@Service
public class NielsenHeuristicService {

    @Autowired
    private NielsenHeuristicRepository nielsenHeuristicRepository;

    @Transactional(readOnly = true)
    public List<NielsenHeuristic> findAllNielsenHeuristic(){
        return nielsenHeuristicRepository.findAll();
    }

    public Object findNielsenHeuristicById(Integer nielsenHeuristicID) {
        return nielsenHeuristicRepository.findById(nielsenHeuristicID).orElseThrow();
    }
    
}
