package com.kakaopay.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "pay_user")
public class User {

    @Id
    @Column(name = "user_id")
    private int userId;
}
