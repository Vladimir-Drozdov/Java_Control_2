package org.example.command.decorators;

import org.example.command.Command;
//Декоратор - структурный паттерн, цель: добавить новую функциональность объекту без изменения его кода
public class TimingDecorator implements Command {

    private final Command inner;

    public TimingDecorator(Command inner) {
        this.inner = inner;
    }

    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        inner.execute();
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + " ms");
    }
}
