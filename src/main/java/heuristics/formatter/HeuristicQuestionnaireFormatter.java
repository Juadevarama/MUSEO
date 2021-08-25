package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicQuestionnaire;
import heuristics.repository.HeuristicQuestionnaireRepository;

@Component
public class HeuristicQuestionnaireFormatter implements Formatter<HeuristicQuestionnaire>{

    private final HeuristicQuestionnaireRepository heuristicQuestionnaires;

    @Autowired
    public HeuristicQuestionnaireFormatter(HeuristicQuestionnaireRepository heuristicQuestionnaires){
        this.heuristicQuestionnaires = heuristicQuestionnaires;
    }
    
    @Override
    public String print(HeuristicQuestionnaire heuristicQuestionnaire, Locale locale){
        return "HeuristicQuestionnaire(id=" + heuristicQuestionnaire.getId() + ", finalHeuristic=" + 
        heuristicQuestionnaire.getFinalHeuristicID() + ", questionnaire=" + heuristicQuestionnaire.getQuestionnaireID() + ")";
    }

    @Override
    public HeuristicQuestionnaire parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicQuestionnaire> findHeuristicQuestionnaires = this.heuristicQuestionnaires.findAll();
        for (HeuristicQuestionnaire plat : findHeuristicQuestionnaires) {
            if (("HeuristicQuestionnaire(id=" + plat.getId() + ", finalHeuristic=" + 
            plat.getFinalHeuristicID() + ", questionnaire=" + plat.getQuestionnaireID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicQuestionnaire not found: " + text, 0);
    }
}
