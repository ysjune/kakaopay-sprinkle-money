package com.kakaopay.service;

import com.kakaopay.domain.*;
import com.kakaopay.dto.MoneySprinkleRequest;
import com.kakaopay.exception.NotExistUserException;
import com.kakaopay.exception.ValidateParameterException;
import com.kakaopay.domain.*;
import com.kakaopay.util.MoneyDistributor;
import com.kakaopay.util.TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MoneySprinkleService {

    private final MoneySprinkleRepository moneySprinkleRepository;
    private final MoneySprinkleItemRepository moneySprinkleItemRepository;
    private final UserRepository userRepository;

    public MoneySprinkleService(MoneySprinkleRepository moneySprinkleRepository, MoneySprinkleItemRepository moneySprinkleItemRepository, UserRepository userRepository) {
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.moneySprinkleItemRepository = moneySprinkleItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String makeSprinkleInfo(MoneySprinkleRequest request) {

        String errors = ParameterValidator.validate(request);
        if(!StringUtils.isEmpty(errors)) {
            throw new ValidateParameterException(errors);
        }

        User userInfo = userRepository.findByUserId(request.getUserId());
        if(ObjectUtils.isEmpty(userInfo)) {
            throw new NotExistUserException();
        }

        String token = TokenGenerator.generate();
        LocalDateTime tokenTime = LocalDateTime.now();

        MoneySprinkle moneySprinkleEntity = MoneySprinkle.builder()
                .sprinkler(request.getUserId())
                .roomId(request.getRoomId())
                .amount(request.getAmount())
                .targetCount(request.getTargetCount())
                .token(token)
                .sprinkledTime(tokenTime)
                .build();

        //뿌리기 정보 저장
        moneySprinkleRepository.save(moneySprinkleEntity);

        // 금액 나누기
        List<Integer> list = MoneyDistributor.distributeMoney(request);

        //받기 정보 저장
        saveSprinkleItem(moneySprinkleEntity, list);

        return token;
    }

    private void saveSprinkleItem(MoneySprinkle moneySprinkleEntity, List<Integer> list) {
        list.stream().forEach(a -> {
            MoneySprinkleItem item = MoneySprinkleItem.builder()
                    .amount(a)
                    .moneySprinkle(moneySprinkleEntity)
                    .build();
            moneySprinkleItemRepository.save(item);
        });
    }


}
