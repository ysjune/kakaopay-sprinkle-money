package com.kakaopay.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface MoneySprinkleRepository extends JpaRepository<MoneySprinkle, Long> {

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        MoneySprinkle findByRoomIdAndToken(String roomId, String token);
}
