package heuristics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import heuristics.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}
