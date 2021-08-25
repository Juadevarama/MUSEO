package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicNielsenHeuristic;

@Repository
public interface HeuristicNielsenHeuristicRepository extends JpaRepository<HeuristicNielsenHeuristic, Integer>{

    
}
