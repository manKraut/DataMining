package com.company;

import java.util.ArrayDeque;

public class AppController {

    ArrayDeque<Thread> threads = new ArrayDeque<>();

    boolean running;
    //App initialization method
    public void run(){
        running = true;
    }

    public void stop() {
        running = false;
    }

    protected void removeTerminatedThreads() {
        threads.removeIf(x -> x.getState().equals(Thread.State.TERMINATED));
    }

}
