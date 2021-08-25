package heuristics.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Platform;
import heuristics.repository.PlatformRepository;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @Transactional(readOnly = true)
    public List<Platform> findAllPlatform(){
        return platformRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Platform findPlatformById(Integer id){
        return platformRepository.findById(id).orElseThrow();
    }
    
}
