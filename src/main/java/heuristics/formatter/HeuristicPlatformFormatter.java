package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicPlatform;
import heuristics.repository.HeuristicPlatformRepository;

@Component
public class HeuristicPlatformFormatter implements Formatter<HeuristicPlatform>{

    private final HeuristicPlatformRepository heuristicPlatforms;

    @Autowired
    public HeuristicPlatformFormatter(HeuristicPlatformRepository heuristicPlatforms){
        this.heuristicPlatforms = heuristicPlatforms;
    }
    
    @Override
    public String print(HeuristicPlatform heuristicPlatform, Locale locale){
        return "HeuristicPlatform(id=" + heuristicPlatform.getId() + ", finalHeuristic=" + 
        heuristicPlatform.getFinalHeuristic() + ", platform=" + heuristicPlatform.getPlatform() + ")";
    }

    @Override
    public HeuristicPlatform parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicPlatform> findHeuristicPlatforms = this.heuristicPlatforms.findAll();
        for (HeuristicPlatform plat : findHeuristicPlatforms) {
            if (("HeuristicPlatform(id=" + plat.getId() + ", finalHeuristic=" + 
            plat.getFinalHeuristic() + ", platform=" + plat.getPlatform() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicPlatform not found: " + text, 0);
    }
}
