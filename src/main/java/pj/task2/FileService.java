package pj.task2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Service
public class FileService {
    private final EntryRepository repository;
    private final String filename;


    public FileService(EntryRepository repository, @Value("${pl.edu.pja.tpo02.filename}") String filename) {
        this.repository = repository;
        this.filename = filename;
    }


    @PostConstruct
    public void readFiles() {
        try{
            List<String> lines = Files.readAllLines(Paths.get("resources/words.csv"));

            for (String line : lines ){
                String[] words = line.split("," );
                Entry entry = new Entry (words[0],words[1],words[2]);
                repository.addEntry(entry);
            }
        } catch (Exception e ) {
            System.err.println("File wasn't loaded: " + filename);
        }
    }
}
