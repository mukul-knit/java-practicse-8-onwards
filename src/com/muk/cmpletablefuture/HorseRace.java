package com.muk.cmpletablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

class Horse {

    private String name;

    public long endTime;

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Horse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Horse run() {
        System.out.println("Galloping " + this.name);
        this.endTime = System.nanoTime();
        System.out.println("horse is " + this.getName() + ", Endtime is = " + Long.toString(this.endTime));
        return this;
    }
}

public class HorseRace {




    public static void main (String ... args) {

        CompletableFuture<Horse> horse1 = CompletableFuture.supplyAsync(() -> new Horse("Sea buiscuit").run());
        CompletableFuture<Horse> horse2 = CompletableFuture.supplyAsync(() -> new Horse("Secreteriat").run());
        CompletableFuture<Horse> horse3 = CompletableFuture.supplyAsync(() -> new Horse("War lord").run());
        CompletableFuture<Horse> horse4 = CompletableFuture.supplyAsync(() -> new Horse("Alcatraz").run());
        long startTime = System.currentTimeMillis();
        System.out.println("Race started at" + Long.toString(startTime));
        CompletableFuture<?> winningHorse = CompletableFuture.anyOf(horse1, horse2, horse3, horse4);

       do {
           System.out.println("Race is continuing");
        } while (!horse1.isDone() && !horse2.isDone() && !horse2.isDone() && !horse4.isDone());

        System.out.println("Winning horse is " + ((Horse)winningHorse.join()).getName());
        long endTimeWinningHorse = ((Horse)winningHorse.join()).getEndTime();
        System.out.println("Winning horse time is " + Long.toString(endTimeWinningHorse));

    }
}
