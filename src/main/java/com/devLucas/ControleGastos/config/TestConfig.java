package com.devLucas.ControleGastos.config;

import com.devLucas.ControleGastos.entity.AccountsPayable;
import com.devLucas.ControleGastos.entity.enums.currentSituation;
import com.devLucas.ControleGastos.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public void run(String... args) throws Exception {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite 1 para iniciar a seção!");
        int valor = sc.nextInt();
        sc.nextLine(); // evitar pular entrada

        if (valor == 1) {

            int opcao = 0;

            do {
                System.out.println("\nBem vindo ao sistema de contas a pagar!");
                System.out.println("========================================");
                System.out.println("=== MENU PRINCIPAL ===");
                System.out.println("1. INCLUSÃO DE CONTAS A PAGAR");
                System.out.println("2. VISUALIZAÇÃO DE TÍTULOS");
                System.out.println("3. MODIFICAÇÃO DE TÍTULO");
                System.out.println("4. Sair");
                System.out.print("Escolha uma opção: ");

                opcao = sc.nextInt();
                sc.nextLine(); // consumir quebra de linha

                switch (opcao) {

                    case 1:
                        System.out.println("Quantas contas deseja incluir? ");
                        int qtdContas = sc.nextInt();
                        sc.nextLine();

                        for (int i = 0; i < qtdContas; i++) {

                            System.out.print("Digite o nome do título: ");
                            String name = sc.nextLine();

                            System.out.print("Digite o valor do título a pagar: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();

                            System.out.print("Digite a data de vencimento (dd/MM/yyyy): ");
                            String dataDigitada = sc.nextLine();

                            LocalDate dueDate = null;
                            try {
                                dueDate = LocalDate.parse(dataDigitada, fmt);
                            } catch (DateTimeParseException e) {
                                System.out.println("Data inválida! Tente novamente.");
                                i--; // força repetir este item
                                continue;
                            }

                            System.out.print("Digite o nome da loja: ");
                            String store = sc.nextLine();

                            System.out.println("Escolha a situação do título:");
                            for (currentSituation s : currentSituation.values()) {
                                System.out.println("- " + s);
                            }

                            String situationStr = sc.nextLine();
                            currentSituation situation;

                            try {
                                situation = currentSituation.valueOf(situationStr.toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Situação inválida! Use: PENDENTE, PAGO ou ATRASADO.");
                                i--;
                                continue;
                            }

                            AccountsPayable ap = new AccountsPayable(null, name, amount, dueDate, store, situation);
                            accountsRepository.save(ap);

                            System.out.println("Conta incluída com sucesso!");
                        }
                        break;

                    case 2:
                        System.out.println("\n=== LISTAGEM DE TÍTULOS ===");
                        accountsRepository.findAll().forEach(System.out::println);
                        break;

                    case 3:
                        System.out.println("Função de modificação ainda não implementada.");
                        break;

                    case 4:
                        System.out.println("Saindo do sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }

            } while (opcao != 4);
        }

        sc.close();
    }
}
