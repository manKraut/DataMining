package com.company.command;

import com.company.command.Command;

import java.util.Deque;

public class PauseThreads implements Command {
    private Deque<Thread> threads;

    public PauseThreads(Deque<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public void execute() {
        System.out.println("Pausing threads");
        threads.forEach(Thread::suspend);
    }
}
