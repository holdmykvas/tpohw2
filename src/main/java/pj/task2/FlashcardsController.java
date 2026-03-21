package pj.task2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class FlashcardsController implements CommandLineRunner {
    private final EntryRepository repository;
    private final FileService fileService;
    private final WordFormatter formatter;
    private final Scanner scanner = new Scanner(System.in);

    public FlashcardsController(EntryRepository repository, FileService fileService, WordFormatter formatter) {
        this.repository = repository;
        this.fileService = fileService;
        this.formatter = formatter;
    }

    private void handleAddWord() {
        System.out.println("Enter a word in English: ");
        String english = scanner.nextLine();

        System.out.println("Enter a word in Polish: ");
        String polish = scanner.nextLine();

        System.out.println("Enter a word in German: ");
        String german = scanner.nextLine();

        Entry entry = new Entry(english.toLowerCase(), polish.toLowerCase(), german.toLowerCase());

        repository.save(entry);
    }

    private void handleDisplayWords() {
        List<Entry> entries = repository.findAll();
        if (entries.isEmpty()) {
            System.out.println("The dictionary is empty");
            return;
        }


        for (Entry entry : repository.findAll()) {
            System.out.println("ID: " + entry.getId() +
                            " | English: " + formatter.format(entry.getEnglish()) +
                            " | Polish: " + formatter.format(entry.getPolish()) +
                            " | German: " + formatter.format(entry.getGerman()));
        }
    }

    private void handleTest() {
        List<Entry> words = repository.findAll();
        if (words.isEmpty()) {
            System.out.println("List is empty.Add words first.");
            return;
        }
        int random = new Random().nextInt(words.size());
        Entry userWord = words.get(random);

        System.out.println("Translate this Polish word: " + userWord.getPolish());

        System.out.println("English: ");
        if (scanner.nextLine().trim().equalsIgnoreCase(userWord.getEnglish())) {
            System.out.println("This is correct!");
        } else {
            System.out.println("Nice try! Correct translation is: " + userWord.getEnglish());

        }

        System.out.println("German: ");
        if (scanner.nextLine().trim().equalsIgnoreCase(userWord.getGerman())) {
            System.out.println("This is correct!");
        } else {
            System.out.println("Nice try! Correct translation is: "+ userWord.getGerman());
        }

    }

    private void handleDeleteWord() {
        System.out.println("Enter an ID of the word to delete it: ");
        try {
            Long id = Long.parseLong(scanner.nextLine().trim());

            if (repository.existsById(id)) {
                repository.deleteById(id);
                System.out.println("The word was deleted");
            }

        } catch (Exception e ) {
            System.out.println("This word is not in the dictionary.");
        }
    }

    public void handleSearch() {
        System.out.println("Enter a word that you are looking for: ");
        String response = scanner.nextLine().trim();

        List<Entry> results = repository.findByEnglishContainingIgnoreCaseOrPolishContainingIgnoreCaseOrGermanContainingIgnoreCase(response,response,response);

        if(results.isEmpty()) {
            System.out.println("No matching word was found.");
        } else {
            for (Entry entry: results) {
                System.out.println("ID: " + entry.getId() +
                        " | English: " + formatter.format(entry.getEnglish()) +
                        " | Polish: " + formatter.format(entry.getPolish()) +
                        " | German: " + formatter.format(entry.getGerman()) + "\n") ;
            }
        }
    }

    private void handleModify() {

    }

    @Override
    public void run(String[] args) throws Exception {
        boolean running = true;

        System.out.println("----------Flashcards by s32876----------");

        while (running) {
            System.out.println("Menu: \n 1. Add words \n 2. Display words \n 3. Test \n " +
                    "4. Search the word \n 5. Modify a word \n 6. Delete a word \n 7. Quit \n Enter your choice: ");
            String response = scanner.nextLine().trim();

            switch (response) {
                case "1": handleAddWord(); break;
                case "2": handleDisplayWords(); break;
                case "3": handleTest(); break;
                case "4": handleSearch(); break;
                case "5": handleModify(); break;
                case "6": handleDeleteWord(); break;
                case "7": running = false; break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
