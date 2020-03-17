package com.miage.altea.battle_api.utils;

import java.util.concurrent.atomic.AtomicLong;


public class SequenceGenerator {
    private static AtomicLong value = new AtomicLong(1);

    public static int getNext() {
        return (int) value.getAndIncrement();
    }
}
