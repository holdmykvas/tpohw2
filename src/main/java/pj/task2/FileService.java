package pj.task2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Scanner;

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
            List<String> lines = Files.readAllLines(resource.get)
        } catch (Exception e ) {
            System.err.println("File wasn't loaded: " + filename);
        }
    }
}
