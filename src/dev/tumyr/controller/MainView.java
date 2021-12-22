package dev.tumyr.controller;

import dev.tumyr.config.DefaultVariables;
import dev.tumyr.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private ListView<Task> fx_tasks;
    @FXML
    private Label fx_jdk_version;
    @FXML
    private Label fx_fx_version;

    private List<Path> paths;
    private final ArrayList<Task> tasks = new ArrayList<Task>();

    @FXML
    public void init(URL baseURL) throws URISyntaxException, IOException {
        URL tasksURL = new URL(baseURL.toString() + DefaultVariables.getPathAoc2021());
        this.paths = FileOperation.listDirectories(Paths.get(tasksURL.toURI()));
        setTasks();
        fx_tasks.getItems().setAll(tasks);
        fx_tasks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fx_jdk_version.setText(System.getProperty("java.vendor.version"));
        fx_fx_version.setText("JavaFX " + System.getProperty("javafx.version"));
    }
    @FXML
    public void handleClickView() {
        Task task = fx_tasks.getSelectionModel().getSelectedItem();
        if(task != null) {
            this.fx_description.setText(task.getDescription());
            this.fx_solution.setText(task.getSolution());
        }
    }
    public void setTasks() {
        this.paths.forEach(varPath -> {
            String targetPath = FileOperation.convertWinPath(varPath.toString());
            String[] arrPath = targetPath.split(DefaultVariables.getFolderAoc2021() + "/");

            if(arrPath.length > 1) {
                String packageName = arrPath[1];
                String description = "";
                String label = packageName.toUpperCase().charAt(0) +
                        packageName.split("\\d+")[0].substring(1) + " " +
                        packageName.split("\\D")[packageName.split("\\D").length - 1]
                                .replace("0", "");
                try {
                    description = FileOperation.getTextFile(targetPath + "/" + DefaultVariables.getDescription());
                    Class<?> dailyClass = Class.forName(DefaultVariables.getClassPathAoc2021() + packageName + "." + packageName.substring(0,1).toUpperCase() + packageName.substring(1));
                    Object daily = dailyClass.getDeclaredConstructor().newInstance();
                    Method method;
                    method = daily.getClass().getMethod(DefaultVariables.getDailyReturnFunction());
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
