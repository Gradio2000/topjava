package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    MealRestController mealRestController;


    @Override
    public void init() {
       appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
       mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Map<String, String[]> parameterMap = request.getParameterMap();
        Meal meal;
        if (parameterMap.containsKey("description")){
            String id = request.getParameter("id");
            meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")), SecurityUtil.getAuthUserId());
            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.create(meal);
            response.sendRedirect("meals");
        }
        else if (parameterMap.containsKey("timeFrom")){

            LocalTime timeFrom = null;
            if (request.getParameter("timeFrom").equals("")){
                timeFrom = LocalTime.of(0, 0);
            } else {
                timeFrom = LocalTime.parse(request.getParameter("timeFrom"));
            }

            LocalTime timeBefore = null;
            if (request.getParameter("timeBefore").equals("")) {
                timeBefore = LocalTime.of(23, 59);
            } else {
                timeBefore = LocalTime.parse(request.getParameter("timeBefore"));
            }

            LocalDate dateFrom = null;
            if (request.getParameter("dateFrom").equals("")) {
                dateFrom = LocalDate.of(1900, 1, 1);
            }
            else {
               dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
            }

            LocalDate dateBefore = null;
            if (request.getParameter("dateBefore").equals("")){
                dateBefore = LocalDate.of(2900, 1, 1);
            }
            else {
               dateBefore = LocalDate.parse(request.getParameter("dateBefore"));
            }

            request.setAttribute("meals",
                     MealsUtil.getFilteredByDateAndTime(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY,
                    dateFrom, dateBefore, timeFrom, timeBefore));


            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.getAuthUserId()) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
