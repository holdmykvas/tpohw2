package pj.task2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String polish;
    private String english;
    private String german;

    public Entry() {

    }

    public Entry(String english,String polish, String german) {
        this.english = english;
        this.polish = polish;
        this.german = german;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getEnglish() { return english;}

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }
}
