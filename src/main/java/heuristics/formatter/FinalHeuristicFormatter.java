package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.FinalHeuristic;
import heuristics.repository.FinalHeuristicRepository;

@Component
public class FinalHeuristicFormatter implements Formatter<FinalHeuristic>{

    private final FinalHeuristicRepository finalHeuristics;

    @Autowired
    public FinalHeuristicFormatter(FinalHeuristicRepository finalHeuristics){
        this.finalHeuristics = finalHeuristics;
    }
    
    @Override
    public String print(FinalHeuristic finalHeuristic, Locale locale){
        return "FinalHeuristic(heuristicString=" + finalHeuristic.getHeuristicString() + ")";
    }

    @Override
    public FinalHeuristic parse(String text, Locale locale) throws ParseException {
        Collection<FinalHeuristic> findFinalHeuristics = this.finalHeuristics.findAll();
        for (FinalHeuristic finalHeuristic : findFinalHeuristics) {
            if (("FinalHeuristic(heuristicString=" + finalHeuristic.getHeuristicString() + ")").equals(text)) {
                return finalHeuristic;
            }
        }
        throw new ParseException("FinalHeuristic not found: " + text, 0);
    }
}
