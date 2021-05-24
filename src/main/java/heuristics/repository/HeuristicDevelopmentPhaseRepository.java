package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicDevelopmentPhase;

@Repository
public interface HeuristicDevelopmentPhaseRepository extends JpaRepository<HeuristicDevelopmentPhase, Integer>{

    
}
