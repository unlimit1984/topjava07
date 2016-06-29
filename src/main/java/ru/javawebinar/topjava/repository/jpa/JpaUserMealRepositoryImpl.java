package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = em.getReference(User.class, userId);
        if (userMeal.isNew()) {
            userMeal.setUser(ref);
            em.persist(userMeal);
            return userMeal;
        } else {
            return (UserMeal) em.createNamedQuery(UserMeal.UPDATE)
                                    .setParameter(1,userMeal.getDateTime())
                                    .setParameter(2,userMeal.getDescription())
                                    .setParameter(3,userMeal.getCalories())
                                    .setParameter(4, userMeal.getId())
                                    .setParameter(5,userId)
                                    .getSingleResult();
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return (UserMeal) em.createNamedQuery(UserMeal.GET)
                                .setParameter("id", id)
                                .setParameter("user_id", userId)
                                .getSingleResult();
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.GET_ALL)
                        .setParameter(1,userId)
                        .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN)
                        .setParameter("startDate", startDate)
                        .setParameter("endDate", endDate)
                        .setParameter("user_id", userId)
                        .getResultList();
    }
}