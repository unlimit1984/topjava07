package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        service.save(userMeal, LoggedUser.id());
        response.sendRedirect("meals");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getId(request);
        LOG.info("Delete {}", id);
        service.delete(id, LoggedUser.id());
        response.sendRedirect("meals");
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserMeal meal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);

    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserMeal meal = service.get(getId(request), LoggedUser.id());
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);

    }


    public void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getAll");
        request.setAttribute("user", LoggedUser.id());
        request.setAttribute("mealList",
                UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    public void showFiltered(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getFiltered");
        request.setAttribute("user", LoggedUser.id());
        LocalDate fromDate;
        try {
            fromDate = LocalDate.parse(request.getParameter("fromDate"));
        } catch (DateTimeParseException e) {
            fromDate = LocalDate.MIN;
        }

        LocalDate toDate;
        try {
            toDate = LocalDate.parse(request.getParameter("toDate"));
        } catch (DateTimeParseException e) {
            toDate = LocalDate.MAX;
        }

        LocalTime fromTime;
        try {
            fromTime = LocalTime.parse(request.getParameter("fromTime"));
        } catch (DateTimeParseException e) {
            fromTime = LocalTime.MIN;
        }

        LocalTime toTime;
        try {
            toTime = LocalTime.parse(request.getParameter("toTime"));
        } catch (DateTimeParseException e) {
            toTime = LocalTime.of(23, 59);
        }

        LocalDateTime from = LocalDateTime.of(fromDate, fromTime);
        LocalDateTime to = LocalDateTime.of(toDate, toTime);

        request.setAttribute("fromDate", fromDate);
        request.setAttribute("fromTime", fromTime);
        request.setAttribute("toDate", toDate);
        request.setAttribute("toTime", toTime);

        if (fromDate.equals(LocalDate.MIN) && toDate.equals(LocalDate.MAX)) {
            request.setAttribute("mealList",
                    UserMealsUtil.getFilteredWithExceeded(service.getAll(LoggedUser.id()), fromTime, toTime, UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
        } else {
            request.setAttribute("mealList",
                    UserMealsUtil.getFilteredByDateTime(service.getAll(LoggedUser.id()), from, to, UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
        }

        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
