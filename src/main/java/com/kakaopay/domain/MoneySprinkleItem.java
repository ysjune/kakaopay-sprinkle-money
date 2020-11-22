package com.kakaopay.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "money_sprinkle_item")
public class MoneySprinkleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "receiver_id")
    private int receiverId;

    @Column(name = "amount")
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprinkle_id")
    private MoneySprinkle moneySprinkle;

    public void catchMoney(int userId) {
        this.receiverId = userId;
    }
}
