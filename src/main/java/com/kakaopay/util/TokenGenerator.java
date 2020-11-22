package com.kakaopay.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class TokenGenerator {

    private static String TOKEN_CANDIDATES = "0123456789abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generate() {
        String token1 = String.valueOf(TOKEN_CANDIDATES.charAt(new Random().nextInt(TOKEN_CANDIDATES.length())));
        String token2 = String.valueOf(TOKEN_CANDIDATES.charAt(new Random().nextInt(TOKEN_CANDIDATES.length())));
        String token3 = String.valueOf(TOKEN_CANDIDATES.charAt(new Random().nextInt(TOKEN_CANDIDATES.length())));

        return new StringBuilder().append(token1).append(token2).append(token3).toString();
    }

}
