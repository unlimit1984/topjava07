package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
//    @Query("DELETE FROM User u WHERE u.id=:id")
//    int delete(@Param("id") int id);
    //@Query("DELETE FROM User u WHERE u.id=?1")
    @Query("DELETE FROM #{#entityName} u WHERE u.id=?1")
    int delete(int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    User findOne(Integer id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.mealList m WHERE u.id=?1 ORDER BY m.dateTime DESC")
    User findOneDeep(Integer id);

    @Override
    List<User> findAll(Sort sort);

    User getByEmail(String email);
}
