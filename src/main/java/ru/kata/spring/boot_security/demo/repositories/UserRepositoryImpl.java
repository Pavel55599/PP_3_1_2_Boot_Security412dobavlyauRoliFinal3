package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }


    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}







