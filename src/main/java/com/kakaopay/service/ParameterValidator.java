package com.kakaopay.service;

import com.kakaopay.dto.MoneySprinkleRequest;
import org.springframework.util.StringUtils;

public class ParameterValidator {
    public static String validate(MoneySprinkleRequest request) {
        StringBuilder errors = new StringBuilder();
        if(StringUtils.isEmpty(request.getRoomId())) {
            errors.append("대화방 식별값이 비어있습니다. \n");
        }
        if(request.getTargetCount() <= 0) {
            errors.append("뿌릴 대상의 숫자가 적잘하지 않습니다. \n");
        }
        if(request.getAmount() <= 0) {
            errors.append("뿌릴 금액이 0원 이하입니다. \n");
        }
        if(request.getUserId() <= 0) {
            errors.append("유저 아이디가 적절하지 않습니다. \n");
        }
        return errors.toString();
    }
}
