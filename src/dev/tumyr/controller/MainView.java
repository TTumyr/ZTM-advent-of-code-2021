package dev.tumyr.controller;

import dev.tumyr.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainView {
    @FXML
    private TextArea fx_description;
    @FXML
    private TextArea fx_solution;
    @FXML
    private ListView fx_tasks;

    private URL baseURL;
    private URL tasksURL;
    private List<Path> paths;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    @FXML
    public void init(URL baseURL) throws URISyntaxException, IOException {
        this.baseURL = baseURL;
        this.tasksURL = new URL(baseURL.toString() + "adventofcode/y2021/");
        this.paths = FileOperation.listDirectories(Paths.get(this.tasksURL.toURI()));
        setTasks();
        fx_tasks.getItems().setAll(tasks);
        fx_tasks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    @FXML
    public void handleClickView() {
        Task task = (Task) fx_tasks.getSelectionModel().getSelectedItem();
        this.fx_description.setText(task.getDescription());
        this.fx_solution.setText(task.getSolution());
    }
    public void setTasks() {
        this.paths.forEach(name -> {
            String[] arrPath = name.toString().split("y2021\\\\");
            String label;
            String description = "";
            if(arrPath.length > 1) {
                label = arrPath[1];
                String toUrl = name.toString();
                try {
                    description = FileOperation.getTextFile(toUrl + "/description.txt");
                    Class<?> dailyClass = Class.forName("dev.tumyr.adventofcode.y2021." + label + "." + label.substring(0,1).toUpperCase() + label.substring(1));
                    Object daily = dailyClass.getDeclaredConstructor().newInstance();
                    Method[] methods = dailyClass.getDeclaredMethods();
                    Method method = null;
                    for (Method value : methods) {
                        if (value.getName().equals("solve")) {
                            method = value;
                        }
                    }
                    method = daily.getClass().getMethod("solve");
                    method.setAccessible(true);
                    String solution = method.invoke(daily).toString();
                    this.tasks.add(new Task(label, description, solution));
                } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
