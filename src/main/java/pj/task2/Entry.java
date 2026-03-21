package pj.task2;

public class Entry {
    private String polish;
    private String english;
    private String german;

    public Entry(String polish,String english, String german) {
        this.english = english;
        this.polish = polish;
        this.german = german;
    }

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
