package com.pictureworks.zensmartjpa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseList {
    private List<ResponseData> data;

    public ResponseList(){
        data = new ArrayList<>();
    }
}
