package com.example.models;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class SubscriberDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Subscriber2 subscriber1) {
        entityManager.persist(subscriber1);
    }

    public void delete(Subscriber2 subscriber1) {
        if (entityManager.contains(subscriber1)) {
            entityManager.remove(subscriber1);
        }
    }

    public List<Subscriber2> getAll() {
        return entityManager.createQuery("from Subscriber2 s LEFT JOIN FETCH s.user").getResultList();
    }
    
    public List<User> getAllSubscribedUsers() {
        return getAll().stream().map(i -> i.getUser()).collect(Collectors.toList());
    }
    
    public Subscriber2 getById(long id) {
        return entityManager.find(Subscriber2.class, id);
    }

    public void update(Subscriber2 subscriber1) {
        entityManager.merge(subscriber1);
    }

}
