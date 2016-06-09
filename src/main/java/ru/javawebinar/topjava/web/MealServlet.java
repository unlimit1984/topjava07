package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.dao.MemoryRepositoryIml;
import ru.javawebinar.topjava.web.dao.UserMealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by vladimir on 05.06.2016.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    private UserMealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new MemoryRepositoryIml(dummydata());
    }

    private List<UserMeal> dummydata() {
        return Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500,1),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,2),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,3),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,4),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,5),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,6)
        );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        String action = request.getParameter("action");

        if(action==null){
            List<UserMealWithExceed> meals = UserMealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("mealList", meals);
            request.getRequestDispatcher("/mealList.jsp").forward(request,response);
        }
        else if(action.equalsIgnoreCase("update")) {
            String mealId = request.getParameter("id");
            if(mealId!=null){
                Integer id = Integer.valueOf(mealId);
                UserMeal m =  repository.getById(id);
                request.setAttribute("meal", new UserMealWithExceed(m.getDateTime(),m.getDescription(), m.getCalories(),false, m.getId()));
            }
            request.getRequestDispatcher("/meal.jsp").forward(request, response);

        } else if(action.equalsIgnoreCase("edit")){
            String description = request.getParameter("description");
            Integer calories = 0;
            try {
                calories = Integer.parseInt(request.getParameter("calories"));
            } catch (NumberFormatException e){
                LOG.error(e.getMessage());
            }
            LocalDateTime dateTime;
            try{
                dateTime = DateTimeUtil.fromString(request.getParameter("dateTime"));
            } catch (DateTimeParseException e){
                LOG.error(e.getParsedString());
                dateTime = LocalDateTime.now();
            }

            String id = request.getParameter("id");
            if(id==null || id.isEmpty()){       //new UserMeal
                UserMeal m = new UserMeal(dateTime,description,calories);
                repository.add(m);
            } else{                             //existing UserMeal
                int mealId = Integer.valueOf(request.getParameter("id"));
                UserMeal m = new UserMeal(dateTime,description,calories,mealId);
                repository.update(m);
            }
            response.sendRedirect("meals");

        } else if(action.equalsIgnoreCase("delete")){
            Integer id = Integer.valueOf(request.getParameter("id"));
            repository.delete(id);
            response.sendRedirect("meals");
        }
    }
}
