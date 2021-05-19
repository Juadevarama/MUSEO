package heuristics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import heuristics.model.HeuristicQuestionnaire;

/* @Repository
public interface HeuristicQuestionnaireRepository extends JpaRepository<HeuristicQuestionnaire, Integer>{

    @Query(nativeQuery = true, value="SELECT * FROM heuristicsquestionnaires WHERE questionnaireID = ?1")
    public List<HeuristicQuestionnaire> findHeuristicQuestionnaireByQuestionnaireId(Integer id);
} */
