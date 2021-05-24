package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicGameAspect;
import heuristics.repository.HeuristicGameAspectRepository;

@Component
public class HeuristicGameAspectFormatter implements Formatter<HeuristicGameAspect>{

    private final HeuristicGameAspectRepository heuristicGameAspects;

    @Autowired
    public HeuristicGameAspectFormatter(HeuristicGameAspectRepository heuristicGameAspects){
        this.heuristicGameAspects = heuristicGameAspects;
    }
    
    @Override
    public String print(HeuristicGameAspect heuristicGameAspect, Locale locale){
        return "HeuristicGameAspect(id=" + heuristicGameAspect.getId() + ", finalHeuristicID=" + 
        heuristicGameAspect.getFinalHeuristicID() + ", gameAspectID=" + heuristicGameAspect.getGameAspectID();
    }

    @Override
    public HeuristicGameAspect parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicGameAspect> findHeuristicGameAspects = this.heuristicGameAspects.findAll();
        for (HeuristicGameAspect plat : findHeuristicGameAspects) {
            if (("HeuristicGameAspect(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", gameAspectID=" + plat.getGameAspectID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicGameAspect not found: " + text, 0);
    }
}
