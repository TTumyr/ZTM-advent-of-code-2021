package dev.tumyr.model;

import dev.tumyr.config.DefaultVariables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class Task {
    private String label;
    private Object classConstructor;
    private String solution;

    public Task(String label, String description, Object classConstructor) {
        this.label = label;
        this.description = description;
        this.classConstructor = classConstructor;
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

    public void setDailyClass(Class<?> dailyClass) {
        this.classConstructor = dailyClass;
    }
    private void solve() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        method = classConstructor.getClass().getMethod(DefaultVariables.getDailyReturnFunction());
        method.setAccessible(true);
        solution = method.invoke(classConstructor).toString();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
