package pj.task2;

import jakarta.persistence.Id;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
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

    @Override
    public void run(String[] args) throws Exception {
        boolean running = true;

        System.out.println("----------Flashcards by s32876----------");

        while (running) {
            System.out.println("""
                    Menu:\s
                     1. Add words\s
                     2. Display words\s
                     3. Test\s
                     4. Search the word\s
                     5. Modify a word\s
                     6. Delete a word\s
                     7. Quit\s
                     Enter your choice:\s""");
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

    private void handleAddWord() {
        System.out.println("Enter a word in English: ");
        String english = scanner.nextLine();

        System.out.println("Enter a word in Polish: ");
        String polish = scanner.nextLine();

        System.out.println("Enter a word in German: ");
        String german = scanner.nextLine();

        Entry entry = new Entry(english, polish, german);

        repository.save(entry);
    }

    private void handleDisplayWords() {
        List<Entry> entries = repository.findAll();
        if (entries.isEmpty()) {
            System.out.println("The dictionary is empty");
            return;
        }
        System.out.println("How would you like to sort your words? \n 1. By English \n 2. By Polish \n 3. By German \n 4. Default \n Enter your choice: ");
        String langSortChoice = scanner.nextLine().trim();

        System.out.println("Which direction should words be sorted? \n 1. Ascending (A-Z) \n 2. Descending (Z-A) \n Enter your choice: ");
        String dirSortChoice = scanner.nextLine().trim();

        Sort.Direction direction = dirSortChoice.equals("2") ? Sort.Direction.DESC : Sort.Direction.ASC;

        String property;
        switch(langSortChoice) {
            case "1": property = "english"; break;
            case "2": property = "polish"; break;
            case "3": property = "german"; break;
            case "4": property = "id"; break;
            default:
                System.out.println("Invalid option.");
                property = "id";
                return;
        }

        Sort sort = Sort.by(direction,property);
        List<Entry> sortedEntries = repository.findAll(sort);
        printList(sortedEntries);

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
            printList(results);
        }
    }

    private void handleModify() {
        System.out.println("Enter a word that you are looking for to modify: ");
        String word = scanner.nextLine().trim();

        List<Entry> results = repository.findByEnglishContainingIgnoreCaseOrPolishContainingIgnoreCaseOrGermanContainingIgnoreCase(word,word,word);

        if (results.isEmpty()) {
            System.out.println("No matching word was found.");
            return;
        } else {
            printList(results);
        }

        Long id = results.getFirst().getId();
        Entry entryToUpdate = repository.findById(id).orElse(null);

        System.out.println("""
                Which translation do you want to modify:\s
                 1. English\s
                 2. Polish\s
                 3. German\s
                 4.All\s
                Enter your choice:""");
        String option = scanner.nextLine().trim();

        switch(option) {
            case "1":
                System.out.println("Enter the word in English: ");
                entryToUpdate.setEnglish(scanner.nextLine().trim().toLowerCase());
                break;

            case "2":
                System.out.println("Enter the word in Polish: ");
                entryToUpdate.setPolish(scanner.nextLine().trim().toLowerCase());
                break;

            case "3":
                System.out.println("Enter the word in German: ");
                entryToUpdate.setGerman(scanner.nextLine().trim().toLowerCase());
                break;

            case "4":
                System.out.println("Enter the word in English: ");
                entryToUpdate.setEnglish(scanner.nextLine().trim().toLowerCase());

                System.out.println("Enter the word in Polish: ");
                entryToUpdate.setPolish(scanner.nextLine().trim().toLowerCase());

                System.out.println("Enter the word in German: ");
                entryToUpdate.setGerman(scanner.nextLine().trim().toLowerCase());
                break;

            default:
                System.out.println("Invalid option.");
                return;
        }

        repository.save(entryToUpdate);

    }

    //HELPERS
    private void printList(List<Entry> entries) {
        System.out.println("\n--- Dictionary ---");
        for (Entry entry : entries) {
            System.out.println("ID: " + entry.getId() +
                    " | English: " + formatter.format(entry.getEnglish()) +
                    " | Polish: " + formatter.format(entry.getPolish()) +
                    " | German: " + formatter.format(entry.getGerman()));
        }
        System.out.println();
    }
}
