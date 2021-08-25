package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Keyword;
import heuristics.repository.KeywordRepository;

@Component
public class KeywordFormatter implements Formatter<Keyword>{

    private final KeywordRepository keywords;

    @Autowired
    public KeywordFormatter(KeywordRepository keywords){
        this.keywords = keywords;
    }
    
    @Override
    public String print(Keyword keyword, Locale locale){
        return "Keyword(id=" + keyword.getId() + ", keywordString=" + keyword.getKeywordString();
    }

    @Override
    public Keyword parse(String text, Locale locale) throws ParseException {
        Collection<Keyword> findKeywords = this.keywords.findAll();
        for (Keyword plat : findKeywords) {
            if (("Keyword(id=" + plat.getId() + ", keywordString=" + plat.getKeywordString() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("Keyword not found: " + text, 0);
    }
}
