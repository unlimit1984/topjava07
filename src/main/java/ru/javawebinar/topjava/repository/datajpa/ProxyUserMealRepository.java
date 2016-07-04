package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vladimir on 04.07.16.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

//    @Override
//    @Transactional
//    UserMeal save(UserMeal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} m WHERE m.id=?1 AND m.user.id=?2")
    int delete(int id, int user_id);


    @Query("SELECT m FROM UserMeal m WHERE m.id=?1 AND m.user.id=?2")
    UserMeal get(int id, int userId);

    @Query(name = UserMeal.ALL_SORTED)
    List<UserMeal> getAll(@Param("userId") int userId);


    @Query(name = UserMeal.GET_BETWEEN)
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
