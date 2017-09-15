package com.helldaisy.restclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;

public class BaseRequest {
    HashMap<String,String> headers,params;
    String baseUrl;
    HttpRequestBase reqtype;
    String ent;

    public BaseRequest(){
        headers=new HashMap<>();
        params=new HashMap<>();
    }

    public CloseableHttpResponse send() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(baseUrl);
        headers.entrySet().stream().forEach((p)->
                reqtype.setHeader(p.getKey(),p.getValue())
        );
        params.entrySet().stream().forEach((p)->
                uriBuilder.addParameter(p.getKey(),p.getValue())
        );
        if (reqtype instanceof HttpEntityEnclosingRequestBase
                &&ent!=null){
            StringEntity entity=new StringEntity(ent);
            ((HttpEntityEnclosingRequestBase) reqtype).setEntity(entity);
        }
        reqtype.setURI(uriBuilder.build());
        CloseableHttpResponse response1 = httpclient.execute(reqtype);
        return response1;
    }
}
