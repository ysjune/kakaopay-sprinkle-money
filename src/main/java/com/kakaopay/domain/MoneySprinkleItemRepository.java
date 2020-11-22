package com.kakaopay.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneySprinkleItemRepository extends JpaRepository<MoneySprinkleItem, Long> {

//    List<MoneySprinkleItem> findAllByTokenAndRoomId(String token, String roomId);
}
