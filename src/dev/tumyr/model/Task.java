package dev.tumyr.model;

import dev.tumyr.config.DefaultVariables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class Task {
    private String label;
    private String dailyClassPath;
    private String solution;

    public Task(String label, String description, String dailyClassPath) {
        this.label = label;
        this.description = description;
        this.dailyClassPath = dailyClassPath;
    }

    private String description;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        try {
            if (Objects.equals(solution, null)) solve();
        }
        catch (Exception e) {
            solution = "An error occurred when trying to get the solution.";
        }
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setDailyClass(String dailyClassPath) {
        this.dailyClassPath = dailyClassPath;
    }
    private void solve() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class<?> dailyClass = Class.forName(dailyClassPath);
        Object dailyObject = dailyClass.getDeclaredConstructor().newInstance();
        Method method;
        method = dailyObject.getClass().getMethod(DefaultVariables.getDailyReturnFunction());
        method.setAccessible(true);
        solution = method.invoke(dailyObject).toString();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
