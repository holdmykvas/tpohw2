package pj.task2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EntryRepositoryImpl implements EntryRepository{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Entry> searchWord(String keyword) {
        return entityManager.createQuery("select e from Entry e where  lower(e.english) LIKE lower(:keyword) OR  " +
                "lower(e.polish) LIKE lower(:keyword) OR lower(e.german) LIKE lower(:keyword)",Entry.class)
                .setParameter("keyword", "%" + keyword + "%").getResultList();
    }

    @Override
    public long count() {
        String query = "select count(e) From Entry e";
        return entityManager.createQuery(query,Long.class).getSingleResult();
    }

    /*States:
                    removed ->
                       ||     |
        transient -> managed <-> DB
                       ||
                    detached
     */

    @Override
    public void save(Entry entry) {
        if (entry.getId() == null) {
            entityManager.persist(entry); // transient -> managed        //offtopic: *persist - постійний*
        } else {
            entityManager.merge(entry); // detached -> managed
        }
    }

    @Override
    public List<Entry> findAll() {
        String query = "Select e From Entry e";
        return entityManager.createQuery(query,Entry.class).getResultList();
    }

    @Override
    public List<Entry> findAll(Sort sort) {
        Sort.Order order = sort.toList().get(0);
        String property = order.getProperty();
        String direction = order.getDirection().name();

        String query = "Select e From Entry e Order By e." + property + " " + direction;
        return entityManager.createQuery(query, Entry.class).getResultList() ;
    }

    @Override
    public Optional<Entry> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Entry.class, id));
    }

    @Override
    public boolean existsById(Long id) {
        if (entityManager.find(Entry.class,id) == null) {
            System.out.println("The id doesn't exist.");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void deleteById(Long id) {
        Entry entryToRemove = entityManager.find(Entry.class,id);

        if (entryToRemove == null) {
            System.out.println("The id doesn't exist.");
        } else {
            entityManager.remove(entryToRemove);
        }
    }
}
