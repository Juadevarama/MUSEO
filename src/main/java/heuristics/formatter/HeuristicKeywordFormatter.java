package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicKeyword;
import heuristics.repository.HeuristicKeywordRepository;

@Component
public class HeuristicKeywordFormatter implements Formatter<HeuristicKeyword>{

    private final HeuristicKeywordRepository heuristicKeywords;

    @Autowired
    public HeuristicKeywordFormatter(HeuristicKeywordRepository heuristicKeywords){
        this.heuristicKeywords = heuristicKeywords;
    }
    
    @Override
    public String print(HeuristicKeyword heuristicKeyword, Locale locale){
        return "HeuristicKeyword(id=" + heuristicKeyword.getId() + ", finalHeuristicID=" + 
        heuristicKeyword.getFinalHeuristicID() + ", keywordID=" + heuristicKeyword.getKeywordID();
    }

    @Override
    public HeuristicKeyword parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicKeyword> findHeuristicKeywords = this.heuristicKeywords.findAll();
        for (HeuristicKeyword plat : findHeuristicKeywords) {
            if (("HeuristicKeyword(id=" + plat.getId() + ", finalHeuristicID=" + 
            plat.getFinalHeuristicID() + ", keywordID=" + plat.getKeywordID() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("HeuristicKeyword not found: " + text, 0);
    }
}
