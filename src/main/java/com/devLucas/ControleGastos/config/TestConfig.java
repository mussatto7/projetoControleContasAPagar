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
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
                        System.out.println("\n=== Lista de Títulos ===");

                        List<AccountsPayable> contas = accountsRepository.findAll();

                        if (contas.isEmpty()) {
                            System.out.println("Nenhum título encontrado.");
                        } else {
                            for (AccountsPayable c : contas) {
                                System.out.println(
                                        "ID: " + c.getId() +
                                                " | Nome: " + c.getTitle() +
                                                " | Valor: " + c.getAmount() +
                                                " | Vencimento: " + c.getDueDate() +
                                                " | Loja: " + c.getStore() +
                                                " | Situação: " + c.getSituation()
                                );
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n=== MODIFICAÇÃO DE TÍTULO ===");
                        System.out.print("Digite o ID do título que deseja modificar: ");

                        long id = sc.nextLong();
                        sc.nextLine();

                        Optional<AccountsPayable> opt = accountsRepository.findById(id);

                        if (opt.isEmpty()) {
                            System.out.println("Título não encontrado!");
                            break; // <-- usado no lugar de return
                        }

                        AccountsPayable conta = opt.get();

                        System.out.println("\nTítulo encontrado:");
                        System.out.println("1. Nome: " + conta.getTitle());
                        System.out.println("2. Valor: " + conta.getAmount());
                        System.out.println("3. Vencimento: " + conta.getDueDate());
                        System.out.println("4. Loja: " + conta.getStore());
                        System.out.println("5. Situação: " + conta.getSituation());
                        System.out.println("--------------------------------------");

                        System.out.println("Qual campo deseja alterar?");
                        System.out.println("1 - Nome");
                        System.out.println("2 - Valor");
                        System.out.println("3 - Data de vencimento");
                        System.out.println("4 - Loja");
                        System.out.println("5 - Situação");
                        System.out.println("6 - Cancelar");

                        System.out.print("Escolha: ");
                        int opc = sc.nextInt();
                        sc.nextLine();

                        switch (opc) {

                            case 1:
                                System.out.print("Novo nome: ");
                                conta.setTitle(sc.nextLine());
                                break;

                            case 2:
                                System.out.print("Novo valor: ");
                                conta.setAmount(sc.nextDouble());
                                sc.nextLine();
                                break;

                            case 3:
                                System.out.print("Nova data (dd/MM/yyyy): ");
                                String dataDigitada2 = sc.nextLine();
                                try {
                                    LocalDate novaData = LocalDate.parse(dataDigitada2, fmt);
                                    conta.setDueDate(novaData);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Data inválida! Alteração cancelada.");
                                    break;
                                }
                                break;

                            case 4:
                                System.out.print("Nova loja: ");
                                conta.setStore(sc.nextLine());
                                break;

                            case 5:
                                System.out.println("Situação (PENDENTE / PAGO / ATRASADO): ");
                                String sit = sc.nextLine().toUpperCase();
                                try {
                                    currentSituation newSit = currentSituation.valueOf(sit);
                                    conta.setSituation(newSit);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Situação inválida! Alteração cancelada.");
                                    break;
                                }
                                break;

                            case 6:
                                System.out.println("Operação cancelada.");
                                break;

                            default:
                                System.out.println("Opção inválida.");
                                break;
                        }

                        accountsRepository.save(conta);
                        System.out.println("Título modificado com sucesso!");
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
