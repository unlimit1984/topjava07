package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by vladimir on 04.07.16.
 */
@ActiveProfiles({Profiles.JDBC, Profiles.ACTIVE_DB})
public class JdbcUserMealServiceTest extends UserMealServiceTest {

}