package fr.lengrand.dialogflowfunapi;

import fr.lengrand.dialogflowfunapi.dialogflow.DialogFlowService;
import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowResponse;
import fr.lengrand.dialogflowfunapi.dialogflow.data.DialogFlowWebHookRequest;
import fr.lengrand.dialogflowfunapi.openbankproject.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@RestController
public class DialogflowFunApiApplication {

	@Autowired
	private Auth auth;

	@Autowired
	private DialogFlowService dialogFlowService;

	public static void main(String[] args) {
		SpringApplication.run(DialogflowFunApiApplication.class, args);
	}

	@PostConstruct
	private void init() {
		auth.safeAuthenticate();
	}

	@PostMapping(value = "/fulfillment")
	public DialogFlowResponse fulfillment(@RequestBody DialogFlowWebHookRequest request) throws IOException, InterruptedException {
        System.out.println("Received request from Dialog Flow!");

        DialogFlowResponse response;
        switch (request.getQueryResult().getIntent().getDisplayName()) {
            case "last.transaction":
				response = dialogFlowService.getLastTransactionRequest();
                break;
			case "send.money":
				response = dialogFlowService.createPaymentRequest(request);
				break;
			case "send.money - yes":
				response = dialogFlowService.createPaymentRequestWithFollowUp(request);
				break;
            default:
                response = new DialogFlowResponse("Sorry, I didn't get that. Can you try again? ");
        }

        return response;
    }

	@GetMapping("/")
	public String hello() {
		return "hello from your bank API!";
	}
}
