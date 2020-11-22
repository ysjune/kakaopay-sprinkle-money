package com.kakaopay.exception;

public class AlreadyReceivedUserException extends RuntimeException {
    public AlreadyReceivedUserException(int amount) {
        super("이미 받은 유저압니다. 받은 금액 : " + amount);
    }
}
