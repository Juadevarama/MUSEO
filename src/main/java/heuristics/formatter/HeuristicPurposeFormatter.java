package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicPurpose;
import heuristics.repository.HeuristicPurposeRepository;

@Component
public class HeuristicPurposeFormatter implements Formatter<HeuristicPurpose>{

    private final HeuristicPurposeRepository heuristicPurposes;

    @Autowired
    public HeuristicPurposeFormatter(HeuristicPurposeRepository heuristicPurposes){
        this.heuristicPurposes = heuristicPurposes;
    }
    
    @Override
    public String print(HeuristicPurpose heuristicPurpose, Locale locale){
        return "HeuristicPurpose(id=" + heuristicPurpose.getId() + ", finalHeuristicID=" + 
        heuristicPurpose.getFinalHeuristicID() + ", purposeID=" + heuristicPurpose.getPurposeID();
    }

    @Override
    public HeuristicPurpose parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicPurpose> findHeuristicPurposes = this.heuristicPurposes.findAll();
        for (HeuristicPurpose plat : findHeuristicPurposes) {
            if (("HeuristicPurpose(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", purposeID=" + plat.getPurposeID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicPurpose not found: " + text, 0);
    }
}
