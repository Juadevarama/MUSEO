package heuristics.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

import heuristics.model.HeuristicRaw;

public interface HeuristicRawRepository extends Repository<HeuristicRaw, Integer>{

    Collection<HeuristicRaw> findAll() throws DataAccessException;
    
}
