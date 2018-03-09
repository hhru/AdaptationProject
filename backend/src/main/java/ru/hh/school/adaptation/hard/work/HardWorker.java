package ru.hh.school.adaptation.hard.work;

import javax.inject.Singleton;

@Singleton
public class HardWorker {

    public void doWork() throws InterruptedException {
        System.out.println("I'm going to do real hard work");
        Thread.sleep(700L);
        System.out.println("Hard work finished");
    }
}
