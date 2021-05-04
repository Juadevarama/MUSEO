package heuristics.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heuristics.model.Keyword;
import heuristics.repository.KeywordRepository;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public List<Keyword> findAllKeyword(){
        return keywordRepository.findAll();
    }
    
}
