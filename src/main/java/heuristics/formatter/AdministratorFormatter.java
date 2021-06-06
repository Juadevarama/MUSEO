/* package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Administrator;
import heuristics.repository.AdministratorRepository;

@Component
public class AdministratorFormatter implements Formatter<Administrator>{

    private final AdministratorRepository administrators;

    @Autowired
    public AdministratorFormatter(AdministratorRepository administrators){
        this.administrators = administrators;
    }
    
    @Override
    public String print(Administrator administrator, Locale locale){
        return "Administrator(administratorname=" + administrator.getUsername() + ")";
    }

    @Override
    public Administrator parse(String text, Locale locale) throws ParseException {
        Collection<Administrator> findAdministrators = this.administrators.findAll();
        for (Administrator administrator : findAdministrators) {
            if (("Administrator(administratorname=" + administrator.getUsername() + ")").equals(text)) {
                return administrator;
            }
        }
        throw new ParseException("Administrator not found: " + text, 0);
    }
}
 */