package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicRaw;

@Repository
public interface HeuristicRawRepository extends JpaRepository<HeuristicRaw, Integer>{

    
}
