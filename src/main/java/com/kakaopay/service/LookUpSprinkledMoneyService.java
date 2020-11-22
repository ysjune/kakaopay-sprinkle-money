package com.kakaopay.service;

import com.kakaopay.dto.SprinkleStatus;
import com.kakaopay.domain.MoneySprinkle;
import com.kakaopay.domain.MoneySprinkleRepository;
import com.kakaopay.domain.User;
import com.kakaopay.domain.UserRepository;
import com.kakaopay.exception.InvalidTokenException;
import com.kakaopay.exception.LookUpTimeExceedException;
import com.kakaopay.exception.NotExistUserException;
import com.kakaopay.exception.NotSprinklerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class LookUpSprinkledMoneyService {

    private final UserRepository userRepository;
    private final MoneySprinkleRepository moneySprinkleRepository;

    public LookUpSprinkledMoneyService(UserRepository userRepository, MoneySprinkleRepository moneySprinkleRepository) {
        this.userRepository = userRepository;
        this.moneySprinkleRepository = moneySprinkleRepository;
    }

    @Transactional
    public SprinkleStatus getSprinkleStatus(String token, String roomId, int userId) {

        User userInfo = userRepository.findByUserId(userId);
        if(ObjectUtils.isEmpty(userInfo)) {
            throw new NotExistUserException();
        }

        MoneySprinkle moneySprinkleInfo = moneySprinkleRepository.findByRoomIdAndToken(roomId, token);
        if(ObjectUtils.isEmpty(moneySprinkleInfo)) {
            throw new InvalidTokenException();
        }

        if(!moneySprinkleInfo.isOwner(userId)) {
            throw new NotSprinklerException();
        }

        if(!moneySprinkleInfo.isAvailTimeGetReceiverInfo()) {
            throw new LookUpTimeExceedException();
        }

        return new SprinkleStatus(moneySprinkleInfo);
    }

}
