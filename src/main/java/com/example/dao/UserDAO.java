package com.example.dao;

import com.example.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByName(String name, String firstName) {
        Query query = entityManager.createNamedQuery("User.findByName");
        query.setParameter("name", name);
        query.setParameter("firstName", firstName);
        return (User) query.getSingleResult();
    }

    public List<User> findByMinimalAge(int minimumAge) {
        Query query = entityManager.createNamedQuery("User.findByMinimalAge");
        query.setParameter("minAge", minimumAge);
        return query.getResultList();
    }
}