package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.UsabilityAspect;

@Repository
public interface UsabilityAspectRepository extends JpaRepository<UsabilityAspect, Integer>{

    
}
