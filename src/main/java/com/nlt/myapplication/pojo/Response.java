package com.nlt.myapplication.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    private Integer code;
    private String message;
    private Object data;

    public static Response success() {
        return new Response(1, "success", null);
    }

    public static Response success(Object data) {
        return new Response(1, "success", data);
    }

    public static Response error(String msg) {
        return new Response(0, msg, null);
    }

}
