package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.GameAspect;
import heuristics.repository.GameAspectRepository;

@Component
public class GameAspectFormatter implements Formatter<GameAspect>{

    private final GameAspectRepository gameAspects;

    @Autowired
    public GameAspectFormatter(GameAspectRepository gameAspects){
        this.gameAspects = gameAspects;
    }
    
    @Override
    public String print(GameAspect gameAspect, Locale locale){
        return "GameAspect(id=" + gameAspect.getId() + ", gameAspectString=" + gameAspect.getGameAspectString();
    }

    @Override
    public GameAspect parse(String text, Locale locale) throws ParseException {
        Collection<GameAspect> findGameAspects = this.gameAspects.findAll();
        for (GameAspect plat : findGameAspects) {
            if (("GameAspect(id=" + plat.getId() + ", gameAspectString=" + plat.getGameAspectString() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("GameAspect not found: " + text, 0);
    }
}
