package com.helldaisy.restclient;

import java.util.List;

public class Repository {
    private List<BaseRequest> requestList;
    private static Repository repository;

    public List<BaseRequest> getRequestList() {
        return requestList;
    }

    public Repository setRequestList(List<BaseRequest> requestList) {
        this.requestList = requestList;
        return this;
    }


    private Repository(){

    }

    public static Repository instance(){
        if (repository==null){
            repository=new Repository();
        }
        return repository;
    }
}
