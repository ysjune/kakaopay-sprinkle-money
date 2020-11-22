package com.kakaopay.service;

import com.kakaopay.domain.*;
import com.kakaopay.exception.*;
import com.kakaopay.domain.*;
import com.kakaopay.exception.*;
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
public class CatchSprinkledMoneyServiceTest {

    private MoneySprinkleRepository moneySprinkleRepository = new MoneySprinkleRepositoryMemoryImpl();

    @InjectMocks
    private CatchSprinkledMoneyService catchSprinkledMoneyService;

    private MoneySprinkleItemRepository moneySprinkleItemRepository = new MoneySprinkleRepositoryItemMemoryImpl();

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        catchSprinkledMoneyService = new CatchSprinkledMoneyService(moneySprinkleRepository, userRepository);
    }

    @Test
    @DisplayName("받기를 하였으나, 유저가 존재하지 않음")
    void notExistUserInfo() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(null);

        //when, then
        assertThrows(NotExistUserException.class, () -> {
            catchSprinkledMoneyService.getMoney("Uc2", 134, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였으나, 토큰이 유효하지 않음")
    void invalidToken() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(134));
        moneySprinkleSaver();

        //when, then
        assertThrows(InvalidTokenException.class, () -> {
            catchSprinkledMoneyService.getMoney("Uc2", 134, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였으나, 뿌리기한 사람이여서 받지 못함")
    void sprinkleOwner() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(100));
        moneySprinkleSaver();

        //when, then
        assertThrows(SprinkleOwnerException.class, () -> {
            catchSprinkledMoneyService.getMoney("43U", 100, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였으나, 10분이 초과하여 받지 못함")
    void getMoneyButTimeExceed() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(101));
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
                .sprinkledTime(LocalDateTime.now().minusMinutes(11L))
                .items(moneySprinkleItems)
                .build()
        );
        moneySprinkleItems.stream().forEach(a -> {
            moneySprinkleItemRepository.save(a);
        });

        //when, then
        assertThrows(SprinkleTimeExceedException.class, () -> {
            catchSprinkledMoneyService.getMoney("43U", 101, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였으나, 이미 받기 이력이 있음")
    void getMoneyButAlreadyHaveItem() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(102));
        moneySprinkleSaver();

        //when, then
        assertThrows(AlreadyReceivedUserException.class, () -> {
            catchSprinkledMoneyService.getMoney("43U", 102, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였으나, 남은 받기 아이템이 없음")
    void getMoneyButNotRemainItem() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(101));
        moneySprinkleSaver();

        //when, then
        assertThrows(NotRemainSprinkleItemException.class, () -> {
            catchSprinkledMoneyService.getMoney("43U", 101, "room1");
        });
    }

    @Test
    @DisplayName("받기를 하였고, 아이템을 받음")
    void getMoneyItem() {
        //given
        given(userRepository.findByUserId(anyInt())).willReturn(new User(101));
        List<MoneySprinkleItem> moneySprinkleItems = Arrays.asList(
                MoneySprinkleItem.builder().receiverId(103).amount(600).build(),
                MoneySprinkleItem.builder().receiverId(0).amount(400).build()
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


        //when
        int getMoney = catchSprinkledMoneyService.getMoney("43U", 101, "room1");

        //then
        assertThat(getMoney).isEqualTo(400);
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
