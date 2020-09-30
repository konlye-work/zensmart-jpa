package com.pictureworks.zensmartjpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Response {
    @JsonIgnore
    private HttpStatus status;

    private ResponseData data;
    private String message;

    public Response(){
        status = HttpStatus.OK;
    }
}
