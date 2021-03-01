package ru.javawebinar.topjava.service.rules;

import org.junit.rules.ExternalResource;

import java.util.Date;

public class MyRule1 extends ExternalResource {

    Date date1;
    Date date2;

    @Override
    protected void before() throws Throwable {
       date1 = new Date();
    }

    @Override
    protected void after() {
        date2 = new Date();
        System.out.println();
        System.out.println("Время выполнения теста: " + (Long.parseLong(String.valueOf(date2.getTime())) -
                Long.parseLong(String.valueOf(date1.getTime()))) + " ms");
    }
}
