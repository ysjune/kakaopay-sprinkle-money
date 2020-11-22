package com.kakaopay.service;

import com.kakaopay.domain.*;
import com.kakaopay.exception.*;
import com.kakaopay.domain.*;
import com.kakaopay.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CatchSprinkledMoneyService {

    private final MoneySprinkleRepository moneySprinkleRepository;
    private final UserRepository userRepository;

    public CatchSprinkledMoneyService(MoneySprinkleRepository moneySprinkleRepository, UserRepository userRepository) {
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public int getMoney(String token, int userId, String roomId) {

        User userInfo = userRepository.findByUserId(userId);
        if(ObjectUtils.isEmpty(userInfo)) {
            throw new NotExistUserException();
        }

        MoneySprinkle moneySprinkleInfo = moneySprinkleRepository.findByRoomIdAndToken(roomId, token);
        if(ObjectUtils.isEmpty(moneySprinkleInfo)) {
            throw new InvalidTokenException();
        }

        if(moneySprinkleInfo.isOwner(userId)) {
            throw new SprinkleOwnerException();
        }

        if(!moneySprinkleInfo.isAvailTimeGetMoney()) {
            throw new SprinkleTimeExceedException();
        }

        return getRemainMoney(userId, moneySprinkleInfo);
    }

    private int getRemainMoney(int userId, MoneySprinkle moneySprinkleInfo) {
        List<MoneySprinkleItem> items = moneySprinkleInfo.getItems();
        Optional<MoneySprinkleItem> receivedItem = items.stream().filter(a -> a.getReceiverId() == userId).findFirst();

        if(receivedItem.isPresent()) {
            throw new AlreadyReceivedUserException(receivedItem.get().getAmount());
        }

        Optional<MoneySprinkleItem> anyItem = items.stream().filter(a -> a.getReceiverId() <= 0).findAny();

        if(!anyItem.isPresent()) {
            throw new NotRemainSprinkleItemException();
        }

        anyItem.get().catchMoney(userId);
        return anyItem.get().getAmount();
    }
}
