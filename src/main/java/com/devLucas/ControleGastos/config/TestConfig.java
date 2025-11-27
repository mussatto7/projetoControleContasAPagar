package com.devLucas.ControleGastos.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import com.devLucas.ControleGastos.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devLucas.ControleGastos.entity.AccountsPayable;
import com.devLucas.ControleGastos.entity.enums.currentSituation;
import com.devLucas.ControleGastos.repositories.AccountsRepository;

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

		if (valor == 1) {
			System.out.println("Bem vindo ao sistema de contas a pagar! ");
			System.out.println("------------------------------------");
			System.out.println("Qual área deseja acessar? \n" + "1 - INCLUSÃO DE CONTA A PAGAR\n"
					+ "2 - VISUALIZAÇÃO DE TÍTULOS\n" + "3 - MODIFICAÇÃO DE TÍTULO\n");
			int valorDigi = sc.nextInt();

			switch (valorDigi) {
			case 1:
				System.out.println("Quantas contas deseja incluir? ");
				int valorDigitado = sc.nextInt();
				sc.nextLine();

				for (int i = 0; i < valorDigitado; i++) {
					System.out.println("Digite o nome do título: ");
					String name = sc.nextLine();

					System.out.println("Digite o valor do título a pagar: ");
					double amount = sc.nextDouble();
					sc.nextLine();

					System.out.println("Digite a data do vencimento do título (dd/MM/yyyy): ");
					String x = sc.nextLine();

					LocalDate dueDate = null;
					try {
						dueDate = LocalDate.parse(x, fmt);
					} catch (DateTimeParseException e) {
						System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
						return;
					}

					System.out.println("Digite o nome da loja: ");
					String store = sc.nextLine();

					System.out.println("Escolha a situação do título: ");
					for (currentSituation s : currentSituation.values()) {
						System.out.println("- " + s);
					}

					String situationStr = sc.nextLine();

					currentSituation situation;
					try {
						situation = currentSituation.valueOf(situationStr.toUpperCase());
					} catch (IllegalArgumentException e) {
						System.out.println("Situação inválida! Use: PENDENTE, PAGO ou ATRASADO.");
						return;
					}

					AccountsPayable accounts = new AccountsPayable(null, name, amount, dueDate, store, situation);

					accountsRepository.saveAll(Arrays.asList(accounts));
				}

				break;
			case 2:
				break;
			case 3:
				break;
			default:
				System.out.println("Digite um valor correto!");
			}
		}
	}
}