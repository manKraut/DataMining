package com.company.command;

import com.company.AppController;
import com.company.command.Command;

public class ExitApp implements Command {
    private AppController application;

    public ExitApp(AppController application) {
        this.application = application;
    }

    @Override
    public void execute() {
        application.stop();
    }
}
