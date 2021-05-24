package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicUsabilityAspect;

@Repository
public interface HeuristicUsabilityAspectRepository extends JpaRepository<HeuristicUsabilityAspect, Integer>{

    
}
