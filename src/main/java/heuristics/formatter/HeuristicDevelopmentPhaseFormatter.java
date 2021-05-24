package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicDevelopmentPhase;
import heuristics.repository.HeuristicDevelopmentPhaseRepository;

@Component
public class HeuristicDevelopmentPhaseFormatter implements Formatter<HeuristicDevelopmentPhase>{

    private final HeuristicDevelopmentPhaseRepository heuristicDevelopmentPhases;

    @Autowired
    public HeuristicDevelopmentPhaseFormatter(HeuristicDevelopmentPhaseRepository heuristicDevelopmentPhases){
        this.heuristicDevelopmentPhases = heuristicDevelopmentPhases;
    }
    
    @Override
    public String print(HeuristicDevelopmentPhase heuristicDevelopmentPhase, Locale locale){
        return "HeuristicDevelopmentPhase(id=" + heuristicDevelopmentPhase.getId() + ", finalHeuristicID=" + 
        heuristicDevelopmentPhase.getFinalHeuristicID() + ", developmentPhaseID=" + heuristicDevelopmentPhase.getDevelopmentPhaseID();
    }

    @Override
    public HeuristicDevelopmentPhase parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicDevelopmentPhase> findHeuristicDevelopmentPhases = this.heuristicDevelopmentPhases.findAll();
        for (HeuristicDevelopmentPhase plat : findHeuristicDevelopmentPhases) {
            if (("HeuristicDevelopmentPhase(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", developmentPhaseID=" + plat.getDevelopmentPhaseID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicDevelopmentPhase not found: " + text, 0);
    }
}
