package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicGameAspect;

@Repository
public interface HeuristicGameAspectRepository extends JpaRepository<HeuristicGameAspect, Integer>{

    
}
