package com.kakaopay.exception;

public class NotRemainSprinkleItemException extends RuntimeException {
    public NotRemainSprinkleItemException() {
        super("받을 수 있는 남은 뿌리기 건이 없습니다.");
    }
}
