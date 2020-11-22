package com.kakaopay.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "money_sprinkle")
public class MoneySprinkle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int sprinkler;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "target_count")
    private int targetCount;

    @Column(name = "token")
    private String token;

    @Column(name = "sprinkled_time")
    private LocalDateTime sprinkledTime;

    @OneToMany(mappedBy = "moneySprinkle")
    private List<MoneySprinkleItem> items = new ArrayList<>();

    public boolean isAvailTimeGetMoney() {
        return !LocalDateTime.now().isAfter(this.sprinkledTime.plusMinutes(10L));
    }

    public boolean isAvailTimeGetReceiverInfo() {
        return !LocalDate.now().isAfter(this.sprinkledTime.plusDays(7L).toLocalDate());
    }

    public boolean isOwner(int userId) {
        return this.sprinkler == userId;
    }
}
