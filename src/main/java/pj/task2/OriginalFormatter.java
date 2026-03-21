package pj.task2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("original")
public class OriginalFormatter implements WordFormatter{
    @Override
    public String format(String word) {
        return word;
    }
}
