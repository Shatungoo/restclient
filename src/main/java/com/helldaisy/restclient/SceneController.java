package com.helldaisy.restclient;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SceneController implements Initializable {
    
    @FXML
    private TextArea request;

    @FXML
    private TextArea responce;

    @FXML
    private ComboBox<String> address;

    @FXML
    private ChoiceBox<HttpRequestBase> method;

    @FXML
    private TableView<Header> headers,params,respParams;

    @FXML
    private TableColumn<Header, String> key,value,keyResp,valueResp;

    
    @FXML
    private void handleButtonAction(ActionEvent event)  {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpRequestBase reqtype=method.getSelectionModel().getSelectedItem();
            URIBuilder uriBuilder = new URIBuilder(address.getValue().toString());
            headers.getItems().stream().forEach((person)->
                    reqtype.setHeader(person.key,person.value)
            );
            params.getItems().stream().forEach((person)->
                    uriBuilder.addParameter(person.key,person.value)
            );
            if (reqtype instanceof HttpEntityEnclosingRequestBase){
                StringEntity entity=new StringEntity(request.getText());
                ((HttpEntityEnclosingRequestBase) reqtype).setEntity(entity);
            }
            reqtype.setURI(uriBuilder.build());
            try(CloseableHttpResponse response1 = httpclient.execute(reqtype)){
                setResp(response1);
            }
        }catch (Exception e){
            responce.setText(e.toString());
        }
    }

    public void setResp(CloseableHttpResponse response) throws IOException{
        responce.setText(EntityUtils.toString(response.getEntity()));
        respParams.getItems().clear();
        respParams.getItems().add(new Header("status",String.valueOf(response.getStatusLine().getStatusCode())
        ));
        Arrays.stream(response.getAllHeaders()).forEach((p)->
                respParams.getItems().add(new Header(p.getName(), p.getValue()))
        );
    }

    class Header{
        String key;
        String value;

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        address.getItems().add("http://vm-postgre.todes.by:8080/restws/auth/login");
        method.getSelectionModel().selectFirst();
        key.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().key));
        value.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().value));

        keyResp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().key));
        valueResp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().value));
        headers.getItems().add(new Header("Content-type", "application/json"));
    }    
}
