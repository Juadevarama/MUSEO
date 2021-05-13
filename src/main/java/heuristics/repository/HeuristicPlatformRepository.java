package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicPlatform;

@Repository
public interface HeuristicPlatformRepository extends JpaRepository<HeuristicPlatform, Integer>{

    
}
