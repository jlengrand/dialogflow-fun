package fr.lengrand.dialogflowfunapi;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.cloud.dialogflow.v2beta1.WebhookRequest;
import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowResponse;
import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowWebHookRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.OpenBankClient;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import fr.lengrand.dialogflowfunapi.openbankproject.data.balance.Balance;
import fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest.PaymentRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;

@SpringBootApplication
@RestController
public class DialogflowFunApiApplication {

	@Autowired
	private Auth auth;

	@Autowired
	private OpenBankClient openBankClient;

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

	@PostMapping(value = "/fulfillment")
	public DialogFlowResponse fulfillment(@RequestBody DialogFlowWebHookRequest request) throws IOException {
		System.out.println("Received fullfillment!");

		System.out.println(request);
		System.out.println("#################" + request.queryResult.intent.displayName);
		System.out.println("#################" + request.queryResult.intent.name);
		return new DialogFlowResponse("You requested to see your balance using " + request.queryResult.intent.displayName);
	}

	@GetMapping("/")
	public String hello() {
		return "hello from your bank API!";
	}

	@GetMapping("/token")
	public String auth() throws IOException, InterruptedException {
		return auth.getToken().isPresent()? auth.getToken().get() : "No token!";
	}

	@GetMapping("/transactions")
	public Transactions transactions()  throws IOException, InterruptedException {
		return openBankClient.getTransactions();
	}

	@GetMapping("/balances")
	public Balance balances() throws IOException, InterruptedException {
		return openBankClient.getBalance();
	}

	@PostMapping("/payment")
	public PaymentRequest payment() throws IOException, InterruptedException {
		return openBankClient.createPaymentRequest();
	}
}
