package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Purpose;
import heuristics.repository.PurposeRepository;

@Component
public class PurposeFormatter implements Formatter<Purpose>{

    private final PurposeRepository purposes;

    @Autowired
    public PurposeFormatter(PurposeRepository purposes){
        this.purposes = purposes;
    }
    
    @Override
    public String print(Purpose purpose, Locale locale){
        return "Purpose(id=" + purpose.getId() + ", purposeString=" + purpose.getPurposeString();
    }

    @Override
    public Purpose parse(String text, Locale locale) throws ParseException {
        Collection<Purpose> findPurposes = this.purposes.findAll();
        for (Purpose plat : findPurposes) {
            if (("Purpose(id=" + plat.getId() + ", purposeString=" + plat.getPurposeString() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("Purpose not found: " + text, 0);
    }
}
