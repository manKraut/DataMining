package com.company.command;

import com.company.command.Command;

import java.util.Deque;

public class ResumeThreads implements Command {
    private Deque<Thread> threads;

    public ResumeThreads(Deque<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public void execute() {
        System.out.println("Resuming threads");

        threads.forEach((t) -> {
            if (t.isAlive()) t.resume();
        });
    }
}
