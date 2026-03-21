package pj.task2;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntryRepository {
    private final List<Entry> entryList = new ArrayList<>();

    public void addEntry(Entry entry) {
        entryList.add(entry);
    }

    public List<Entry> getAllEntries() {
        return entryList;
    }
}
