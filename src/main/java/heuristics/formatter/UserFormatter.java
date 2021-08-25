package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.User;
import heuristics.repository.UserRepository;

@Component
public class UserFormatter implements Formatter<User>{

    private final UserRepository users;

    @Autowired
    public UserFormatter(UserRepository users){
        this.users = users;
    }
    
    @Override
    public String print(User user, Locale locale){
        return "User(username=" + user.getUsername() + ")";
    }

    @Override
    public User parse(String text, Locale locale) throws ParseException {
        Collection<User> findUsers = this.users.findAll();
        for (User user : findUsers) {
            if (("User(username=" + user.getUsername() + ")").equals(text)) {
                return user;
            }
        }
        throw new ParseException("User not found: " + text, 0);
    }
}
