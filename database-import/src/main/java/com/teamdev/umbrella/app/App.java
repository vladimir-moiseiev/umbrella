package com.teamdev.umbrella.app;

import com.teamdev.umbrella.app.parsing.ProviderParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;

public class App {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:import.cfg.xml");

        ProviderParser providerParser = context.getBean(ProviderParser.class);


        providerParser.parse();
        int x = 1;
    }
}
