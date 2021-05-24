package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicKeyword;

@Repository
public interface HeuristicKeywordRepository extends JpaRepository<HeuristicKeyword, Integer>{

    
}
