package pj.task2;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EntryRepository{
    List<Entry> searchWord(String keyword);
    public long count();
    public void save(Entry entry);
    public List<Entry> findAll();
    public List<Entry> findAll(Sort sort);
    public Optional<Entry> findById(Long id);
    public boolean existsById(Long id);
    public void deleteById(Long id);

}
