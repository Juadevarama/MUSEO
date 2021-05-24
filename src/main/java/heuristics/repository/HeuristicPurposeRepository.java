package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicPurpose;

@Repository
public interface HeuristicPurposeRepository extends JpaRepository<HeuristicPurpose, Integer>{

    
}
