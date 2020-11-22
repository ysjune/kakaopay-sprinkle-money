package com.kakaopay.service;

import com.kakaopay.domain.*;
import com.kakaopay.dto.MoneySprinkleRequest;
import com.kakaopay.exception.NotExistUserException;
import com.kakaopay.exception.ValidateParameterException;
import com.kakaopay.domain.*;
import com.kakaopay.query.MoneySprinkleRepositoryItemMemoryImpl;
import com.kakaopay.query.MoneySprinkleRepositoryMemoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MoneySprinkleServiceTest {

    private MoneySprinkleRepository moneySprinkleRepository = new MoneySprinkleRepositoryMemoryImpl();

    @InjectMocks
    private MoneySprinkleService MoneySprinkleService;

    private MoneySprinkleItemRepository moneySprinkleItemRepository = new MoneySprinkleRepositoryItemMemoryImpl();

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MoneySprinkleService = new MoneySprinkleService(moneySprinkleRepository, moneySprinkleItemRepository, userRepository);
    }

    @Test
    @DisplayName("뿌리기를 진행하였으나 필수 파라미터 미포함으로 에러")
    void notValidParam() {

        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .userId(123)
                .build();

        //when, then
        assertThrows(ValidateParameterException.class, () -> {
            MoneySprinkleService.makeSprinkleInfo(request);
        });
    }

    @Test
    @DisplayName("뿌리기를 진행하였으나 존재하지 않는 유저로 에러")
    void notExistUser() {

        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .roomId("room11")
                .userId(123)
                .build();

        //when, then
        assertThrows(NotExistUserException.class, () -> {
            MoneySprinkleService.makeSprinkleInfo(request);
        });
    }

    @Test
    @DisplayName("4명에게 뿌리기를 하여 4개의 받기 아이템 생성됨")
    void distribute4User() {
        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .roomId("room11")
                .userId(123)
                .build();

        given(userRepository.findByUserId(anyInt())).willReturn(User.builder().userId(123).build());

        //when
        String token = MoneySprinkleService.makeSprinkleInfo(request);

        //then
        List<MoneySprinkleItem> all = moneySprinkleItemRepository.findAll();
        assertThat(all.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("뿌리기를 진행하였을 때 정상적으로 동작하여 토큰이 발급됨")
    void moneySprinkleServiceTest1() {

        //given
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(4)
                .roomId("room11")
                .userId(123)
                .build();
        given(userRepository.findByUserId(anyInt())).willReturn(User.builder().userId(123).build());

        //when
        String token = MoneySprinkleService.makeSprinkleInfo(request);

        //then
        assertThat(token).isNotNull();
    }


    @AfterEach
    void tearDown() {
        moneySprinkleRepository.deleteAll();
        moneySprinkleItemRepository.deleteAll();
    }
}
