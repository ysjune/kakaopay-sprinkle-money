package com.kakaopay.util;

import com.kakaopay.dto.MoneySprinkleRequest;

import java.util.ArrayList;
import java.util.List;

public class MoneyDistributor {

    public static List<Integer> distributeMoney(MoneySprinkleRequest request) {
        int amount = request.getAmount();
        int targetCount = request.getTargetCount();
        int divideMoney = amount/targetCount;
        List<Integer> list = new ArrayList();
        for (int i = 0; i < targetCount - 1; i++) {
            list.add(divideMoney);
        }
        int remainMoney = amount - (divideMoney * (targetCount -1));
        list.add(remainMoney);
        return list;
    }
}
