package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicNielsenHeuristic;
import heuristics.repository.HeuristicNielsenHeuristicRepository;

@Component
public class HeuristicNielsenHeuristicFormatter implements Formatter<HeuristicNielsenHeuristic>{

    private final HeuristicNielsenHeuristicRepository heuristicNielsenHeuristics;

    @Autowired
    public HeuristicNielsenHeuristicFormatter(HeuristicNielsenHeuristicRepository heuristicNielsenHeuristics){
        this.heuristicNielsenHeuristics = heuristicNielsenHeuristics;
    }
    
    @Override
    public String print(HeuristicNielsenHeuristic heuristicNielsenHeuristic, Locale locale){
        return "HeuristicNielsenHeuristic(id=" + heuristicNielsenHeuristic.getId() + ", finalHeuristicID=" + 
        heuristicNielsenHeuristic.getFinalHeuristicID() + ", nielsenHeuristicID=" + heuristicNielsenHeuristic.getNielsenHeuristicID();
    }

    @Override
    public HeuristicNielsenHeuristic parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicNielsenHeuristic> findHeuristicNielsenHeuristics = this.heuristicNielsenHeuristics.findAll();
        for (HeuristicNielsenHeuristic plat : findHeuristicNielsenHeuristics) {
            if (("HeuristicNielsenHeuristic(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", nielsenHeuristicID=" + plat.getNielsenHeuristicID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicNielsenHeuristic not found: " + text, 0);
    }
}
