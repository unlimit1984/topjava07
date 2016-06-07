package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.dao.CRUDRepository;
import ru.javawebinar.topjava.web.dao.MemoryRepositoryIml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by vladimir on 05.06.2016.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    private CRUDRepository repository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            List<UserMealWithExceed> meals = UserMealsUtil.getFilteredWithExceeded(repository.getAllUserMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("mealList", meals);
            request.getRequestDispatcher("/mealList.jsp").forward(request,response);
        }
        else if(action.equalsIgnoreCase("update")) {
            String mealId = request.getParameter("id");
            if(mealId!=null){
                Integer id = Integer.valueOf(mealId);
                UserMeal m =  repository.getMealById(id);
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
                dateTime = LocalDateTime.parse(request.getParameter("dateTime"),formatter);
            } catch (DateTimeParseException e){
                LOG.error(e.getParsedString());
                dateTime = LocalDateTime.now();
            }

            String id = request.getParameter("id");
            if(id==null || id.isEmpty()){       //new UserMeal
                int maxId=0;
                try {
                    maxId = repository.getAllUserMeals()
                            .stream()
                            .max((m1, m2) -> Integer.compare(m1.getId(), m2.getId())).get().getId();
                }
                catch(NoSuchElementException e){
                    LOG.error(e.getMessage());
                }
                UserMeal m = new UserMeal(dateTime,description,calories,maxId+1);
                repository.addMeal(m);
            } else{                             //existing UserMeal
                int mealId = Integer.valueOf(request.getParameter("id"));
                UserMeal m = new UserMeal(dateTime,description,calories,mealId);
                repository.updateMeal(m);
            }
            response.sendRedirect("meals");

        } else if(action.equalsIgnoreCase("delete")){
            Integer id = Integer.valueOf(request.getParameter("id"));
            repository.deleteMeal(id);
            response.sendRedirect("meals");
        }
    }
}
