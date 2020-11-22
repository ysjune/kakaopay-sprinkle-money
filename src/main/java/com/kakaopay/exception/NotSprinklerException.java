package com.kakaopay.exception;

public class NotSprinklerException extends RuntimeException {
    public NotSprinklerException() {
        super("뿌린 사람만 조회가 가능합니다.");
    }
}
