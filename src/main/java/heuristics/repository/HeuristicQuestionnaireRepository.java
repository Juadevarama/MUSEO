package heuristics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import heuristics.model.HeuristicQuestionnaire;

@Repository
public interface HeuristicQuestionnaireRepository extends JpaRepository<HeuristicQuestionnaire, Integer>{

    @Query("SELECT * q.heuristicQuestionnaire FROM Questionnaire h WHERE q.id = ?1")
    List<HeuristicQuestionnaire> findHeuristicQuestionnaireByQuestionnaireId(Integer id);

    
}
