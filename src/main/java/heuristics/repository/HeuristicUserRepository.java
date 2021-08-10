package heuristics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import heuristics.model.HeuristicUser;

@Repository
public interface HeuristicUserRepository extends JpaRepository<HeuristicUser, Integer>{

    List<HeuristicUser> findHeuristicUsersByUserID(Integer id);

    List<HeuristicUser> findHeuristicUsersByquestionnaireID(Integer id);

}
