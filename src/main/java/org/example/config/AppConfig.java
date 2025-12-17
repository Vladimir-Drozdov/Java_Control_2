package org.example.config;

import org.example.console.AppConsole;
import org.example.facade.*;
import org.example.factory.DomainFactory;
import org.example.proxy.BankAccountRepositoryProxy;
import org.example.repo.*;
import org.example.visitor.CsvExporter;
import org.example.visitor.ExporterVisitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    DomainFactory domainFactory() {
        return new DomainFactory();
    }

    @Bean
    BankAccountRepository bankAccountRepository() {
        return new BankAccountRepositoryProxy(
                new BankAccountRepositoryMemory(),
                "accounts.csv"
        );
    }

    @Bean
    BankAccountFacade bankAccountFacade(
            BankAccountRepository repo,
            DomainFactory factory
    ) {
        return new BankAccountFacade(repo, factory);
    }

    @Bean
    CategoryFacade categoryFacade(DomainFactory factory) {
        return new CategoryFacade(new CategoryRepository(), factory);
    }

    @Bean
    OperationFacade operationFacade(DomainFactory factory) {
        return new OperationFacade(new OperationRepositoryMemory(), factory);
    }

    @Bean
    ExporterVisitor exporterVisitor() {
        return new CsvExporter();
    }

    @Bean
    AppConsole appConsole(
            BankAccountFacade bankFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade,
            ExporterVisitor exporter
    ) {
        return new AppConsole(
                bankFacade,
                categoryFacade,
                operationFacade,
                exporter
        );
    }
}
