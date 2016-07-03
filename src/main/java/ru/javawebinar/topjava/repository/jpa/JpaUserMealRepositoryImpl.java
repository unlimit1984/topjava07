package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        //User ref = em.find(User.class, userId);
        userMeal.setUser(ref);

        if (userMeal.isNew()) {
            em.persist(userMeal);
        } else {
            //1st approach
            if(get(userMeal.getId(), userId) == null){
                return null;
            }
            em.merge(userMeal);

            //2nd approach
//            int updateRows =  em.createNamedQuery(UserMeal.UPDATE)
//                    .setParameter(1, userMeal.getDateTime())
//                    .setParameter(2, userMeal.getDescription())
//                    .setParameter(3, userMeal.getCalories())
//                    .setParameter(4, userMeal.getId())
//                    .setParameter(5, userId)
//                    .executeUpdate();
//            if(updateRows!=1){
//                //return em.find(UserMeal.class, userMeal.getId());
//                //return em.getReference(UserMeal.class, userMeal.getId());
//                return null;
//            }
        }
        return userMeal;
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
        try {
            return (UserMeal) em.createNamedQuery(UserMeal.GET)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.GET_ALL, UserMeal.class)
                        .setParameter(1,userId)
                        .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class)
                        .setParameter("startDate", startDate)
                        .setParameter("endDate", endDate)
                        .setParameter("user_id", userId)
                        .getResultList();
    }
}