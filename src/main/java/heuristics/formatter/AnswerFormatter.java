package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Answer;
import heuristics.repository.AnswerRepository;

@Component
public class AnswerFormatter implements Formatter<Answer>{

    private final AnswerRepository answers;

    @Autowired
    public AnswerFormatter(AnswerRepository answers){
        this.answers = answers;
    }
    
    @Override
    public String print(Answer answer, Locale locale){
        return "Answer(id=" + answer.getId() + ", heuristicQuestionnaireID=" + answer.getHeuristicQuestionnaireID() + 
        ", userID=" + answer.getUserID() + ", ansString=" + answer.getAnsString() + ")";
    }

    @Override
    public Answer parse(String text, Locale locale) throws ParseException {
        Collection<Answer> findAnswers = this.answers.findAll();
        for (Answer ans : findAnswers) {
            if (("Answer(id=" + ans.getId() + ", heuristicQuestionnaireID=" + ans.getHeuristicQuestionnaireID() + 
            ", userID=" + ans.getUserID() + ", ansString=" + ans.getAnsString() + ")").equals(text)) {
                return ans;
            }
        }
        throw new ParseException("Answer not found: " + text, 0);
    }
}
