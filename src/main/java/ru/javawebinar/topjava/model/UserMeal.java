package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query =         "DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = UserMeal.UPDATE, query =         "UPDATE UserMeal m SET m.dateTime=?1, m.description=?2, m.calories=?3 " +
                                                                "WHERE m.id=?4 AND m.user.id=?5"),
        @NamedQuery(name = UserMeal.GET, query =            "SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = UserMeal.GET_ALL, query =        "SELECT m FROM UserMeal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.GET_BETWEEN, query =    "SELECT m FROM UserMeal m " +
                                                            "WHERE  m.user.id=:user_id AND " +
                                                                    "m.dateTime BETWEEN :startDate AND :endDate " +
                                                                    //":startDate<=m.dateTime AND m.dateTime<=:endDate " +
                                                                "ORDER BY m.dateTime DESC")
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String UPDATE = "UserMeal.update";
    public static final String GET = "UserMeal.get";
    public static final String GET_ALL = "UserMeal.getAll";
    public static final String GET_BETWEEN = "UserMeal.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
