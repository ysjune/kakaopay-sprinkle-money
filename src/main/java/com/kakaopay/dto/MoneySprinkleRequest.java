package com.kakaopay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneySprinkleRequest {
    private int amount;
    private int targetCount;
    private int userId;
    private String roomId;

    public void setUserInfo(int userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
