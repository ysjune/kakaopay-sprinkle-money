package com.kakaopay.util;

import com.kakaopay.dto.MoneySprinkleRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyDistributeTest {

    @Test
    @DisplayName("분배를 하였고, 총 분배 갯수가 일치")
    void equalDistributeCount() {

        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .build();

        //when
        List<Integer> distributeList = MoneyDistributor.distributeMoney(request);

        //then
        assertThat(distributeList.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("분배를 하였고, 모두 같은 금액")
    void distributeEqualMoney() {

        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .build();

        //when
        List<Integer> distributeList = MoneyDistributor.distributeMoney(request);

        //then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(distributeList.get(0)).isEqualTo(250);
            soft.assertThat(distributeList.get(1)).isEqualTo(250);
            soft.assertThat(distributeList.get(2)).isEqualTo(250);
            soft.assertThat(distributeList.get(3)).isEqualTo(250);
        });
    }
}
