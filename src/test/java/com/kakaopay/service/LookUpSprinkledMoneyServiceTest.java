package com.kakaopay.service;

import com.kakaopay.domain.*;
import com.kakaopay.dto.SprinkleStatus;
import com.kakaopay.exception.InvalidTokenException;
import com.kakaopay.exception.LookUpTimeExceedException;
import com.kakaopay.exception.NotExistUserException;
import com.kakaopay.exception.NotSprinklerException;
import com.kakaopay.domain.*;
import com.kakaopay.query.MoneySprinkleRepositoryItemMemoryImpl;
import com.kakaopay.query.MoneySprinkleRepositoryMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LookUpSprinkledMoneyServiceTest {

    private MoneySprinkleRepository moneySprinkleRepository = new MoneySprinkleRepositoryMemoryImpl();

    @InjectMocks
    private LookUpSprinkledMoneyService lookUpSprinkledMoneyService;

    private MoneySprinkleItemRepository moneySprinkleItemRepository = new MoneySprinkleRepositoryItemMemoryImpl();

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        lookUpSprinkledMoneyService = new LookUpSprinkledMoneyService(userRepository, moneySprinkleRepository);
    }

    @Test
    @DisplayName("조회를 진행하였으나, 존재하지 않는 유저로 에러")
    void notExistUser() {

        //given
        given(userRepository.findByUserId(anyInt())).willReturn(null);

        //when, then
        assertThrows(NotExistUserException.class, () -> {
            lookUpSprinkledMoneyService.getSprinkleStatus("4uC", "room1", 123);
        });
    }

    @Test
    @DisplayName("조회를 진행하였으나, 토큰이 유효하지 않음")
    void invalidToken() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(134));
        moneySprinkleSaver();

        //when, then
        assertThrows(InvalidTokenException.class, () -> {
            lookUpSprinkledMoneyService.getSprinkleStatus("Uc2", "room1", 123);
        });
    }

    @Test
    @DisplayName("조회를 진행하였으나, 뿌린 사람이 아님")
    void notSprinkler() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(134));
        moneySprinkleSaver();

        //when, then
        assertThrows(NotSprinklerException.class, () -> {
            lookUpSprinkledMoneyService.getSprinkleStatus("43U", "room1", 123);
        });
    }

    @Test
    @DisplayName("조회를 진행하였으나, 7일이 경과하여 조회 못함")
    void lookUpTimeExceed() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(100));
        moneySprinkleRepository.save(MoneySprinkle.builder()
                .sprinkler(100)
                .roomId("room1")
                .amount(1000)
                .targetCount(2)
                .token("43U")
                .sprinkledTime(LocalDateTime.now().minusDays(8L))
                .build()
        );

        //when, then
        assertThrows(LookUpTimeExceedException.class, () -> {
            lookUpSprinkledMoneyService.getSprinkleStatus("43U", "room1", 100);
        });
    }

    @Test
    @DisplayName("조회를 진행하였고, 받기 현황이 조회됨")
    void lookUpStatus() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(100));
        moneySprinkleSaver();

        //when
        SprinkleStatus status = lookUpSprinkledMoneyService.getSprinkleStatus("43U", "room1", 100);

        //then
        assertThat(status.getReceivedAmount()).isEqualTo(1000);
        assertThat(status.getTotalAmount()).isEqualTo(1000);
        assertThat(status.getReceivers().get(0).getAmount()).isEqualTo(400);

    }


    private void moneySprinkleSaver() {
        List<MoneySprinkleItem> moneySprinkleItems = Arrays.asList(
                MoneySprinkleItem.builder().receiverId(102).amount(400).build(),
                MoneySprinkleItem.builder().receiverId(103).amount(600).build()
        );

        moneySprinkleRepository.save(MoneySprinkle.builder()
                .sprinkler(100)
                .roomId("room1")
                .amount(1000)
                .targetCount(2)
                .token("43U")
                .sprinkledTime(LocalDateTime.now())
                .items(moneySprinkleItems)
                .build()
        );
        moneySprinkleItems.stream().forEach(a -> {
            moneySprinkleItemRepository.save(a);
        });
    }


}
