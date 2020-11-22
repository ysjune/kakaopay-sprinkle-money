package com.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse ok() {
        this.code = 200;
        this.message = "success";
        this.data = null;
        return this;
    }

    public ApiResponse okWithData(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
        return this;
    }

    public ApiResponse fail() {
        this.code = 400;
        this.message = "fail";
        this.data = null;
        return this;
    }

    public ApiResponse failWithMessage(String message) {
        this.code = 400;
        this.message = message;
        this.data = null;
        return this;
    }
}
