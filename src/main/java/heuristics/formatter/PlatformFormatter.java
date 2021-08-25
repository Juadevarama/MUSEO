package heuristics.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import heuristics.model.Platform;
import heuristics.repository.PlatformRepository;

@Component
public class PlatformFormatter implements Formatter<Platform>{

    private final PlatformRepository platforms;

    @Autowired
    public PlatformFormatter(PlatformRepository platforms){
        this.platforms = platforms;
    }
    
    @Override
    public String print(Platform platform, Locale locale){
        return "Platform(id=" + platform.getId() + ", platformString=" + platform.getPlatformString();
    }

    @Override
    public Platform parse(String text, Locale locale) throws ParseException {
        Collection<Platform> findPlatforms = this.platforms.findAll();
        for (Platform plat : findPlatforms) {
            if (("Platform(id=" + plat.getId() + ", platformString=" + plat.getPlatformString() + ")").equals(text)) {
                return plat;
            }
        }
        throw new ParseException("Platform not found: " + text, 0);
    }
}
