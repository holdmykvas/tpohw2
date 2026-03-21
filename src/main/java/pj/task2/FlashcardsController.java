package pj.task2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class FlashcardsController implements CommandLineRunner {
    private final EntryRepository repository;
    private final Scanner scanner = new Scanner(System.in);

    public FlashcardsController(EntryRepository repository) {
        this.repository = repository;
    }

    private void handleAddWord() {
        System.out.println("Enter a word in English: ");
        String english = scanner.nextLine();

        System.out.println("Enter a word in Polish: ");
        String polish = scanner.nextLine();

        System.out.println("Enter a word in German: ");
        String german = scanner.nextLine();

        Entry entry = new Entry(english.toLowerCase(), polish.toLowerCase(), german.toLowerCase());

        repository.addEntry(entry);
    }

    private void handleDisplayWords() {
        for (Entry entry : repository.getAllEntries()) {
            System.out.println("English: " + entry.getEnglish() + " | Polish: " + entry.getPolish() + " | German: " + entry.getGerman());
        }
    }

    private void handleTest() {
        List<Entry> words = repository.getAllEntries();
        if (words.isEmpty()) {
            System.out.println("List is empty.Add words first.");
            return;
        }
        int random = (int) (Math.random() * words.size());
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

    @Override
    public void run(String[] args) throws Exception {
        boolean running = true;

        System.out.println("----------Flashcards by s32876----------");

        while (running) {
            System.out.println("Menu: \n 1. Add words \n 2.Display words \n 3. Test \n 4.Quit \n Enter your choice: ");
            String response = scanner.nextLine();

            switch (response) {
                case "1": handleAddWord(); break;
                case "2": handleDisplayWords(); break;
                case "3": handleTest(); break;
                case "4": running = false; break;
            }
        }
    }
}
