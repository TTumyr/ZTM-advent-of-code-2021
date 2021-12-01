package dev.tumyr;

import dev.tumyr.controller.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    private URL baseURL = getClass().getResource("");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main.fxml"));
        Parent root = loader.load();
        MainView controller = loader.getController();
        controller.init(baseURL);
        primaryStage.setTitle("ZTM advent of code 2021");
        primaryStage.setScene(new Scene(root, 320, 400));
        primaryStage.show();
    }
}
