package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Questionnaire;
import heuristics.repository.QuestionnaireRepository;

@Component
public class QuestionnaireFormatter implements Formatter<Questionnaire>{

    private final QuestionnaireRepository questionnaires;

    @Autowired
    public QuestionnaireFormatter(QuestionnaireRepository questionnaires){
        this.questionnaires = questionnaires;
    }
    
    @Override
    public String print(Questionnaire questionnaire, Locale locale){
        return "Questionnaire(id=" + questionnaire.getId() + ", questionnaireString=" + questionnaire.getProduct();
    }

    @Override
    public Questionnaire parse(String text, Locale locale) throws ParseException {
        Collection<Questionnaire> findQuestionnaires = this.questionnaires.findAll();
        for (Questionnaire plat : findQuestionnaires) {
            if (("Questionnaire(id=" + plat.getId() + ", questionnaireString=" + plat.getProduct() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("Questionnaire not found: " + text, 0);
    }
}
