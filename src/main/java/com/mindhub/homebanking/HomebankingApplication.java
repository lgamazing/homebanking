package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);


	}

	;


	@Bean //lo puedo usar cuando quiera.
	public CommandLineRunner initData(ClienteRepository clienteRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return args -> {



			Cliente cliente1 = new Cliente("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Cliente cliente3 = new Cliente("Rodri", "Ezequiel", "TuMami3@gmail.com", passwordEncoder.encode("1234"));
			Cliente cliente5 = new Cliente("Pedro", "Lionel", "Lion321@mindhub.com", passwordEncoder.encode("12345"));
			Cliente clienteAdmin = new Cliente("Pepito","pepito","ADMINMINDHUB@ADMIN.COM",passwordEncoder.encode("123"));
			Account cuenta1 = new Account("VIN-00000001", LocalDateTime.now().plusDays(21), 5000.0, AccountType.CORRIENTE, true);
			Account cuenta2 = new Account("VIN-00000002",LocalDateTime.now(),7000.0, AccountType.CORRIENTE, true);
			Account cuenta3 = new Account("VIN-00000003", LocalDateTime.now(), 50000.000, AccountType.CORRIENTE, true);
			Account cuenta4 = new Account("VIN-00000004", LocalDateTime.now(), 50000.000, AccountType.CORRIENTE, true);
			Account cuenta5 = new Account("VIN-00000005", LocalDateTime.now(), 50000.000, AccountType.CORRIENTE, true);



			Transaction transferencia4 = new Transaction(CREDITO, +500, "Pase de battalla", LocalDateTime.now());
			Transaction transferencia = new Transaction(TransactionType.DEBITO, -4000, "Comida del Mac", LocalDateTime.now());
			Transaction transferencia2 = new Transaction(CREDITO, +4000, "Gemas del clash royale", LocalDateTime.now());
			Transaction transferencia3 = new Transaction(CREDITO, +15000, "Auto Fitito", LocalDateTime.now());
			Transaction transferencia5 = new Transaction(DEBITO, -99000, "Comida del Mac", LocalDateTime.now());
			Transaction transferencia6 = new Transaction(DEBITO, -3000, "Comida del Mac", LocalDateTime.now());


			clienteRepository.save(cliente1);
			clienteRepository.save(cliente5);
			clienteRepository.save(cliente3);
			clienteRepository.save(clienteAdmin);



			cliente1.addAccount(cuenta1);
			cliente1.addAccount(cuenta2);
			cliente3.addAccount(cuenta3);
			cliente3.addAccount(cuenta4);
			cliente3.addAccount(cuenta5);



			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			accountRepository.save(cuenta3);
			accountRepository.save(cuenta4);
			accountRepository.save(cuenta5);




			cuenta1.addTransaction(transferencia);
			cuenta1.addTransaction(transferencia2);
			cuenta1.addTransaction(transferencia3);
			cuenta3.addTransaction(transferencia4);
			cuenta1.addTransaction(transferencia5);
			cuenta1.addTransaction(transferencia6);

			transactionRepository.save(transferencia);
			transactionRepository.save(transferencia2);
			transactionRepository.save(transferencia3);
			transactionRepository.save(transferencia4);
			transactionRepository.save(transferencia5);
			transactionRepository.save(transferencia6);






			Loan loan1 = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000.0, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 300000.0, List.of(6,12,24,36));


			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60,cliente1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12,cliente1, loan2);

			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24,cliente3, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36,cliente3, loan3);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);


			Card Card1 = new Card(cliente1.getPrimerNombre() + " " + cliente1.getApellido(), CardType.DEBITO, CardColor.GOLD, "4233-5122-4312-9949", 912, LocalDate.now(), LocalDate.now().plusYears(5), true);
			Card Card2 = new Card(cliente1.getPrimerNombre() + " " + cliente1.getApellido(), CardType.CREDITO, CardColor.TITANIUM, "4233-2311-5151-9959", 320, LocalDate.now(), LocalDate.now().plusYears(5), true);

			Card Card3 = new Card("Rodri Ezequiel", CardType.CREDITO, CardColor.SILVER, "4233-2002-4312-6220", 612, LocalDate.now(), LocalDate.now().plusYears(5), true);
			Card Card4 = new Card("Rodri Ezequiel", CardType.CREDITO, CardColor.TITANIUM, "4233-1822-4312-7391", 951, LocalDate.now(), LocalDate.now().plusYears(5), true);

			cliente1.addCards(Card1);
			cliente1.addCards(Card2);

			cliente3.addCards(Card3);
			cliente3.addCards(Card4);



			cardRepository.save(Card1);
			cardRepository.save(Card2);

			cardRepository.save(Card3);
			cardRepository.save(Card4);



		};
	}
}
