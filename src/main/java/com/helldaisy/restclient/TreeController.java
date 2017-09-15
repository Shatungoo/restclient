package com.helldaisy.restclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;
public class TreeController implements Initializable {
    @FXML
    private ListView<BaseRequest> requestList;

    Repository repository;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<BaseRequest> data = FXCollections.observableArrayList(
               new BaseRequest().setBaseUrl("ping"),
                new BaseRequest().setBaseUrl("pong"));
        requestList.setItems(data);

//        Scene secondScene = new Scene(root, 800, 400);
//        Stage secondStage = new Stage();
//        secondStage.setTitle("Your to-do.....");
//        secondStage.setScene(secondScene);
//        secondStage.initStyle(StageStyle.DECORATED);
//        secondStage.initModality(Modality.NONE);
//        secondStage.initOwner(primaryStage);
//        primaryStage.toFront();
//        secondStage.show();
    }
}
