/* package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Critic;
import heuristics.repository.CriticRepository;

@Component
public class CriticFormatter implements Formatter<Critic>{

    private final CriticRepository critics;

    @Autowired
    public CriticFormatter(CriticRepository critics){
        this.critics = critics;
    }
    
    @Override
    public String print(Critic critic, Locale locale){
        return "Critic(criticname=" + critic.getUsername() + ")";
    }

    @Override
    public Critic parse(String text, Locale locale) throws ParseException {
        Collection<Critic> findCritics = this.critics.findAll();
        for (Critic critic : findCritics) {
            if (("Critic(criticname=" + critic.getUsername() + ")").equals(text)) {
                return critic;
            }
        }
        throw new ParseException("Critic not found: " + text, 0);
    }
} */
