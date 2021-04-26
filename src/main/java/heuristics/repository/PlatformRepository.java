package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Integer>{

    
}
