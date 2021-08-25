package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.FinalHeuristic;

@Repository
public interface FinalHeuristicRepository extends JpaRepository<FinalHeuristic, Integer>{

    
}
