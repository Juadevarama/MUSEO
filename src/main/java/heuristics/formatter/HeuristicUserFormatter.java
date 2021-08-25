package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.HeuristicUser;
import heuristics.repository.HeuristicUserRepository;

@Component
public class HeuristicUserFormatter implements Formatter<HeuristicUser>{

    private final HeuristicUserRepository heuristicUsers;

    @Autowired
    public HeuristicUserFormatter(HeuristicUserRepository heuristicUsers){
        this.heuristicUsers = heuristicUsers;
    }
    
    @Override
    public String print(HeuristicUser heuristicUser, Locale locale){
        return "HeuristicUser(id=" + heuristicUser.getId() + ", questionnaireID=" + heuristicUser.getQuestionnaireID() + 
        ", userID=" + heuristicUser.getUserID() + ", owner=" + heuristicUser.getOwner() + ", filled=" + heuristicUser.getFilled() + ")";
    }

    @Override
    public HeuristicUser parse(String text, Locale locale) throws ParseException {
        Collection<HeuristicUser> findHeuristicUsers = this.heuristicUsers.findAll();
        for (HeuristicUser hU : findHeuristicUsers) {
            if (("HeuristicUser(id=" + hU.getId() + ", questionnaireID=" + hU.getQuestionnaireID() + 
            ", userID=" + hU.getUserID() + ", owner=" + hU.getOwner() + ", filled=" + hU.getFilled() + ")").equals(text)) {
                return hU;
            }
        }
        throw new ParseException("HeuristicUser not found: " + text, 0);
    }
}
