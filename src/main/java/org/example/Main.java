package org.example;

import org.example.config.AppConfig;
import org.example.console.AppConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(AppConsole.class).run();
    }
}
