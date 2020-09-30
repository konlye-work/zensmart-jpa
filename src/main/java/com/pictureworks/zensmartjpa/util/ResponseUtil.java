package com.pictureworks.zensmartjpa.util;

import com.pictureworks.zensmartjpa.model.Response;
import com.pictureworks.zensmartjpa.model.ResponseList;
import com.pictureworks.zensmartjpa.model.ResponseData;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseUtil {
    private ResponseUtil(){

    }

    public static Response getResponse(HttpStatus status, String errorMessage) {
        Response response = new Response();
        response.setStatus(status);
        response.setMessage(errorMessage);
        return response;
    }

    public static Response getResponse(ResponseData arg) {
        Response response = new Response();
        response.setData(arg);
        return response;
    }
}
