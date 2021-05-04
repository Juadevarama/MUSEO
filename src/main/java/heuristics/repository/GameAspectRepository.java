package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.GameAspect;

@Repository
public interface GameAspectRepository extends JpaRepository<GameAspect, Integer>{

    
}
