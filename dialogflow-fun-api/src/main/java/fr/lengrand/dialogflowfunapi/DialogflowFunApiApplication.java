package fr.lengrand.dialogflowfunapi;

import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.transactions.TransactionsHandler;
import fr.lengrand.dialogflowfunapi.openbankproject.transactions.data.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@RestController
public class DialogflowFunApiApplication {

	@Autowired
	private Auth auth;

	@Autowired
	private TransactionsHandler transactionsHandler;

	public static void main(String[] args) {
		SpringApplication.run(DialogflowFunApiApplication.class, args);
	}

	@PostConstruct
	private void init() {
		try {
			auth.authenticate();
		} catch (IOException | InterruptedException e) {
			System.out.println("Error while authenticating!"); // TODO : Use a logger
		}
	}

	@GetMapping("/")
	public String hello() {
		return "hello world!";
	}

	@GetMapping("/auth")
	public String auth() throws IOException, InterruptedException {
		auth.authenticate(); // TODO: Should be done on start!
		return auth.getToken().isPresent()? auth.getToken().get() : "No token!";
	}

	@GetMapping("/transactions")
	public Transactions transactions()  throws IOException, InterruptedException {
		return transactionsHandler.getTransactions();
	}

}
