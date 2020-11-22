package com.kakaopay.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("방에 유효하지 않은 토큰입니다.");
    }
}
