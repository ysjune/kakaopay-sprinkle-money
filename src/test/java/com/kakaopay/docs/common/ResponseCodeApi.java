package com.kakaopay.docs.common;

import com.kakaopay.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseCodeApi {

    @GetMapping("/response-code")
    public ApiResponse getResponseCode() {
        return new ApiResponse().ok();
    }
}
