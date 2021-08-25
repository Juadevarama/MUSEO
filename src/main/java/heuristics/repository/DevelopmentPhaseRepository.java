package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.DevelopmentPhase;

@Repository
public interface DevelopmentPhaseRepository extends JpaRepository<DevelopmentPhase, Integer>{

    
}
