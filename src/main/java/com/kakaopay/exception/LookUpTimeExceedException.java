package com.kakaopay.exception;

public class LookUpTimeExceedException extends RuntimeException {
    public LookUpTimeExceedException() {
        super("뿌린 건에 대한 조회는 7일까지만 가능합니다.");
    }
}
