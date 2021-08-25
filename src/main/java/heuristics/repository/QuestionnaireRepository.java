package heuristics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heuristics.model.Questionnaire;


@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer>{

}
