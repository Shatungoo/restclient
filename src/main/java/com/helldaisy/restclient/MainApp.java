package com.helldaisy.restclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Tree.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("RestClient");
        stage.setScene(scene);
        stage.show();
//        Parent sec = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
//                Scene secondScene = new Scene(sec, 800, 400);
//        Stage secondStage = new Stage();
//        secondStage.setTitle("Request");
//        secondStage.setScene(secondScene);
//        secondStage.initStyle(StageStyle.DECORATED);
//        secondStage.initOwner(scene.getWindow());
//        secondStage.initModality(Modality.NONE);
//        secondStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
