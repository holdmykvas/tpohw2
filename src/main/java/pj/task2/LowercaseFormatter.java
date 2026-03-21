package pj.task2;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("lowercase")
public class LowercaseFormatter implements WordFormatter{
    @Override
    public String format(String word) {
        return word.toLowerCase();
    }
}
