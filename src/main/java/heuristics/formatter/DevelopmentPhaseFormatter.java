package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.DevelopmentPhase;
import heuristics.repository.DevelopmentPhaseRepository;

@Component
public class DevelopmentPhaseFormatter implements Formatter<DevelopmentPhase>{

    private final DevelopmentPhaseRepository developmentphases;

    @Autowired
    public DevelopmentPhaseFormatter(DevelopmentPhaseRepository developmentphases){
        this.developmentphases = developmentphases;
    }
    
    @Override
    public String print(DevelopmentPhase developmentphase, Locale locale){
        return "DevelopmentPhase(id=" + developmentphase.getId() + ", developmentPhaseString=" + developmentphase.getDevelopmentPhaseString();
    }

    @Override
    public DevelopmentPhase parse(String text, Locale locale) throws ParseException {
        Collection<DevelopmentPhase> findDevelopmentphases = this.developmentphases.findAll();
        for (DevelopmentPhase dev : findDevelopmentphases) {
            if (("DevelopmentPhase(id=" + dev.getId() + ", developmentPhaseString=" + dev.getDevelopmentPhaseString() + ")").equals(text)) {
                return dev;
            }
        }
        throw new ParseException("DevelopmentPhase not found: " + text, 0);
    }
}
