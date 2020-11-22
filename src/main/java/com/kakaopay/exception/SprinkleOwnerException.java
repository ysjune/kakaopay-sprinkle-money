package com.kakaopay.exception;

public class SprinkleOwnerException extends RuntimeException {
    public SprinkleOwnerException() {
        super("자신이 뿌리기한 건은 자신이 받을 수 없습니다.");
    }
}
