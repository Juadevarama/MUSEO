package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Purpose;
import heuristics.repository.PurposeRepository;

@Service
public class PurposeService {

    @Autowired
    private PurposeRepository purposeRepository;

    @Transactional(readOnly = true)
    public List<Purpose> findAllPurpose(){
        return purposeRepository.findAll();
    }
    
}
