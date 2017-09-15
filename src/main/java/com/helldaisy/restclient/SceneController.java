package com.helldaisy.restclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
public class SceneController implements Initializable {

    public BaseRequest request;
    
    @FXML
    private TextArea content;

    @FXML
    private TextArea responce;

    @FXML
    private ComboBox<String> address;

    @FXML
    private ChoiceBox<HttpRequestBase> method;

    @FXML
    private TableView<KeyValue<String,String>> headers,params,respParams;

    @FXML
    private TableColumn<KeyValue<String,String>, String> key,value,keyResp,valueResp,key1,value1;

    
    @FXML
    private void handleButtonAction(ActionEvent event)  {
        try {
            headers.getItems().stream().forEach(item->request.headers.put(item.getKey(),item.getValue()));
            params.getItems().stream().forEach(item->request.params.put(item.getKey(),item.getValue()));
            setResp(request.send());
        }catch (Exception e){
            e.printStackTrace();
            responce.setText(e.toString());
        }
    }

    @FXML
    private void addHeader(ActionEvent event) {
        headers.getItems().add(new KeyValue<String, String>("  ", ""));
    }

    @FXML
    private void deleteHeader(ActionEvent event) {
        headers.getItems().remove(headers.getSelectionModel().getSelectedItem());
    }

    public void setResp(CloseableHttpResponse response) throws IOException{
        responce.setText(EntityUtils.toString(response.getEntity()));
        respParams.getItems().clear();
        respParams.getItems().add(new KeyValue<>("status",String.valueOf(response.getStatusLine().getStatusCode())
        ));
        Arrays.stream(response.getAllHeaders()).forEach((p)->
                respParams.getItems().add(new KeyValue(p.getName(), p.getValue()))
        );
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        request=new BaseRequest();
        method.setItems(FXCollections.observableArrayList(
                new HttpGet(),
                new HttpPost()
                )
        );
        method.setConverter(new StringConverter<HttpRequestBase>() {
            @Override
            public String toString(HttpRequestBase req) {
                return req.getMethod();
            }

            @Override
            public HttpRequestBase fromString(String string) {
                return null;
            }
        });
        method.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                        request.reqtype=newValue
        );

        content.textProperty().addListener((obs, old, niu)->
           request.ent=niu);

        address.setCellFactory(comboBox -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        request.baseUrl=item;
                    }
                }
            };
        });

        address.getItems().add("http://vm-postgre.todes.by:8080/restws/auth/login");

        method.getSelectionModel().selectFirst();
        key.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        value.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
        key.setCellFactory(TextFieldTableCell.<KeyValue<String,String>>forTableColumn());
        value.setCellFactory(TextFieldTableCell.<KeyValue<String,String>>forTableColumn());
        value.setOnEditCommit( t ->
           t.getTableView()
               .getItems()
               .get(t.getTablePosition()
                   .getRow())
                   .setValue(t.getNewValue())
        );
        key.setOnEditCommit( t ->
                t.getTableView()
                        .getItems()
                        .get(t.getTablePosition()
                                .getRow())
                        .setKey(t.getNewValue())
        );
        value1.setOnEditCommit( t ->
                t.getTableView()
                        .getItems()
                        .get(t.getTablePosition()
                                .getRow())
                        .setValue(t.getNewValue())
        );
        key1.setOnEditCommit( t ->
                t.getTableView()
                        .getItems()
                        .get(t.getTablePosition()
                                .getRow())
                        .setKey(t.getNewValue())
        );
        keyResp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        valueResp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
        headers.getItems().add(new KeyValue("Content-type", "application/json")) ;
    }


}
