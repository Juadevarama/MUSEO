package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.NielsenHeuristic;
import heuristics.repository.NielsenHeuristicRepository;

@Component
public class NielsenHeuristicFormatter implements Formatter<NielsenHeuristic>{

    private final NielsenHeuristicRepository nielsenHeuristics;

    @Autowired
    public NielsenHeuristicFormatter(NielsenHeuristicRepository nielsenHeuristics){
        this.nielsenHeuristics = nielsenHeuristics;
    }
    
    @Override
    public String print(NielsenHeuristic nielsenHeuristic, Locale locale){
        return "NielsenHeuristic(id=" + nielsenHeuristic.getId() + ", nielsenHeuristicString=" + nielsenHeuristic.getNielsenHeuristicString();
    }

    @Override
    public NielsenHeuristic parse(String text, Locale locale) throws ParseException {
        Collection<NielsenHeuristic> findNielsenHeuristics = this.nielsenHeuristics.findAll();
        for (NielsenHeuristic plat : findNielsenHeuristics) {
            if (("NielsenHeuristic(id=" + plat.getId() + ", nielsenHeuristicString=" + plat.getNielsenHeuristicString() + 
            ", numbInteger=" + plat.getNumbInteger() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("NielsenHeuristic not found: " + text, 0);
    }
}
