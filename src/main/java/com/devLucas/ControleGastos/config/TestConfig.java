package com.devLucas.ControleGastos.config;

import com.devLucas.ControleGastos.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
