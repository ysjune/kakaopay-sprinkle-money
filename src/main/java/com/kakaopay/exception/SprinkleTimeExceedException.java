package com.kakaopay.exception;

public class SprinkleTimeExceedException extends RuntimeException {
    public SprinkleTimeExceedException() {
        super("뿌린지 10분이 지나서 받을 수 없습니다.");
    }
}
