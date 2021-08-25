package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.Purpose;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose, Integer>{

    
}
