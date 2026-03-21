package pj.task2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.StandardOpenOption;


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
        if (repository.count() == 0 ) {
            System.out.println("Loading words from CSV.");
            try {
                List<String> lines = Files.readAllLines(Paths.get(filename));

                for (String line : lines) {
                    String[] words = line.split(",");
                    Entry entry = new Entry(words[0], words[1], words[2]);
                    repository.save(entry);
                }
            } catch (Exception e) {
                System.err.println("File wasn't loaded: " + filename);
            }
        } else {
            System.out.println("Database has been initialized before.No changes made.");
        }
    }
}
