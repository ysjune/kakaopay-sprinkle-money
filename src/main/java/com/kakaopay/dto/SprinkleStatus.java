package com.kakaopay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakaopay.domain.MoneySprinkle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprinkleStatus {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sprinkledTime;
    private int totalAmount;
    private int receivedAmount;
    private List<receiverInfo> receivers;

    public SprinkleStatus(MoneySprinkle moneySprinkleInfo) {
        this.totalAmount = moneySprinkleInfo.getAmount();
        this.sprinkledTime = moneySprinkleInfo.getSprinkledTime();

        List<receiverInfo> receiverInfos = moneySprinkleInfo.getItems().stream().filter(a -> {
            if (a.getReceiverId() > 0) {
                return true;
            }
            return false;
        }).map(v -> {
            return receiverInfo.builder()
                    .amount(v.getAmount())
                    .userId(v.getReceiverId())
                    .build();
        }).collect(Collectors.toList());

        this.receivers = receiverInfos;
        this.receivedAmount = receiverInfos.stream().mapToInt(v -> v.getAmount()).sum();
    }
}
