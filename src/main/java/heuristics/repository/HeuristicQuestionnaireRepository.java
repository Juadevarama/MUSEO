package heuristics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import heuristics.model.HeuristicQuestionnaire;

@Repository
public interface HeuristicQuestionnaireRepository extends JpaRepository<HeuristicQuestionnaire, Integer>{

    @Query("SELECT h FROM HeuristicQuestionnaire h WHERE h.questionnaireID =:id")
    public List<HeuristicQuestionnaire> findHeuristicQuestionnaireByQuestionnaireId(@Param("id") int id);
}
