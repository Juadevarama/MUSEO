package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.UsabilityAspect;
import heuristics.repository.UsabilityAspectRepository;

@Component
public class UsabilityAspectFormatter implements Formatter<UsabilityAspect>{

    private final UsabilityAspectRepository usabilityAspects;

    @Autowired
    public UsabilityAspectFormatter(UsabilityAspectRepository usabilityAspects){
        this.usabilityAspects = usabilityAspects;
    }
    
    @Override
    public String print(UsabilityAspect usabilityAspect, Locale locale){
        return "UsabilityAspect(id=" + usabilityAspect.getId() + ", usabilityAspectString=" + usabilityAspect.getUsabilityAspectString();
    }

    @Override
    public UsabilityAspect parse(String text, Locale locale) throws ParseException {
        Collection<UsabilityAspect> findUsabilityAspects = this.usabilityAspects.findAll();
        for (UsabilityAspect plat : findUsabilityAspects) {
            if (("UsabilityAspect(id=" + plat.getId() + ", usabilityAspectString=" + plat.getUsabilityAspectString() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("UsabilityAspect not found: " + text, 0);
    }
}
