package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicUsabilityAspect;
import heuristics.repository.HeuristicUsabilityAspectRepository;

@Component
public class HeuristicUsabilityAspectFormatter implements Formatter<HeuristicUsabilityAspect>{

    private final HeuristicUsabilityAspectRepository heuristicUsabilityAspects;

    @Autowired
    public HeuristicUsabilityAspectFormatter(HeuristicUsabilityAspectRepository heuristicUsabilityAspects){
        this.heuristicUsabilityAspects = heuristicUsabilityAspects;
    }
    
    @Override
    public String print(HeuristicUsabilityAspect heuristicUsabilityAspect, Locale locale){
        return "HeuristicUsabilityAspect(id=" + heuristicUsabilityAspect.getId() + ", finalHeuristicID=" + 
        heuristicUsabilityAspect.getFinalHeuristicID() + ", usabilityAspectID=" + heuristicUsabilityAspect.getUsabilityAspectID();
    }

    @Override
    public HeuristicUsabilityAspect parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicUsabilityAspect> findHeuristicUsabilityAspects = this.heuristicUsabilityAspects.findAll();
        for (HeuristicUsabilityAspect plat : findHeuristicUsabilityAspects) {
            if (("HeuristicUsabilityAspect(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", usabilityAspectID=" + plat.getUsabilityAspectID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicUsabilityAspect not found: " + text, 0);
    }
}
