package com.example.demoorder.seeder;

import com.sun.istack.internal.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    private static Logger logger = Logger.getLogger(ApplicationSeeder.class.getSimpleName());
    @Autowired
    ProductSeeder productSeeder;
    @Autowired
    ArticleSeeder articleSeeder;

    @Override
    public void run(String...args) throws Exception {
        logger.log(level)
    }
}
