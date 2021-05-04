package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.NielsenHeuristic;

@Repository
public interface NielsenHeuristicRepository extends JpaRepository<NielsenHeuristic, Integer>{

    
}
